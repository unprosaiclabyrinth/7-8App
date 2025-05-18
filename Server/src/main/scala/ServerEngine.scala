import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}
import scala.concurrent.{Future, blocking}
import scala.concurrent.ExecutionContext.Implicits.global
import com.typesafe.scalalogging.LazyLogging
import scala.util.Random

import Rank.*
import Suit.*

object ServerEngine extends LazyLogging:
  private case class Player(id: 1 | 2,
                            socket: Socket,
                            in: ObjectInputStream,
                            out: ObjectOutputStream):
    def send(msg: Message): Unit =
      out.writeObject(msg)
      out.flush()

    def recv: Message =
      blocking(in.readObject.asInstanceOf[Message])

  def start(port: Int): Unit =
    // Set up server socket
    val server = new ServerSocket(port)
    logger.info(s"Listening on port $port")

    // Accept exactly 2 players
    val p1 = acceptPlayer(1, server)
    p1.send(WaitingFor1ToStart)
    val p2 = acceptPlayer(2, server)
    val player: Map[1 | 2, Player] = Map(1 -> p1, 2 -> p2)
    logger.info("Two players connected. Stopped listening. Setting up game...")

    // Initialize a shuffled deck
    val shuffled = Random.shuffle(
      Suit.values.filter(_ != Back).flatMap(s =>
        Rank.values.filter(_.value > 7).map(r => Card(r, s))
      ).toList ++ List(Card(Seven, Spades), Card(Seven, Hearts))
    )
    assert(shuffled.length == 30)

    val deals: List[List[List[Card]]] = List(
      shuffled.take(10).zipWithIndex,
      shuffled.slice(10, 20).zipWithIndex,
      shuffled.drop(20).zipWithIndex
    ) map (third => List(
      third.filter(_._2 % 2 == 0).map(_._1),
      third.filter(_._2 % 2 == 1).map(_._1)
    ))
    assert(deals.length == 3 && deals.forall(_.length == 2) && deals.forall(_.forall(_.length == 5)))

    def broadcast(msg: Message): Unit =
      player.values.foreach(_.send(msg))
    
    // Initialize game semantics
    val dealer: 1 | 2 = if Random.nextBoolean then 1 else 2
    val trumpCaller: 1 | 2 = if dealer == 1 then 2 else 1

    val dealsByRole: Map[1 | 2, (List[Card], List[Card], List[Card])] = Map(
      trumpCaller -> (deals.head.head, deals(1).head, deals(2).head),
      dealer -> (deals.head(1), deals(1)(1), deals(2)(1))
    )

    // Send top deals to both players
    player(trumpCaller).send(TopDeal(dealsByRole(trumpCaller)._1))
    player(dealer).send(TopDeal(dealsByRole(dealer)._1))
    logger.info("Sent top deals to both players.")
    
    // Send trump request to nondealer
  
    broadcast(CallingTrump(trumpCaller))
    logger.info(s"Sent trump request to player $trumpCaller")

    // Receive trump from nondealer and broadcast it
    val trump = player(trumpCaller).recv.asInstanceOf[Trump].trump
    broadcast(Trump(trump))
    logger.info(s"Received trump: $trump, and broadcasted it.")
    
    // Send bottom deals to both players
    player(trumpCaller).send(BottomDownDeal(dealsByRole(trumpCaller)._2))
    player(dealer).send(BottomDownDeal(dealsByRole(dealer)._2))
    player(trumpCaller).send(BottomUpDeal(dealsByRole(trumpCaller)._3))
    player(dealer).send(BottomUpDeal(dealsByRole(dealer)._3))
    logger.info("Sent bottom deals to both players.")
    logger.info("Game setup complete. Starting game...")
    
    playGame(
      dealsByRole(1)._1, dealsByRole(2)._1,
      dealsByRole(1)._2, dealsByRole(2)._2,
      dealsByRole(1)._3, dealsByRole(2)._3,
      trump, trumpCaller
    )
  
  private def acceptPlayer(id: 1 | 2, server: ServerSocket): Player =
    val sock = blocking(server.accept())
    logger.info(s"Player $id connected from ${sock.getInetAddress}:${sock.getPort}")

    // create object streams -- OOS first, then flush it, then OIS
    val out = new ObjectOutputStream(sock.getOutputStream)
    out.flush()
    val in = new ObjectInputStream(sock.getInputStream)

    Player(id, sock, in, out)

  private def playGame(top1: List[Card],
                       top2: List[Card],
                       bottomDown1: List[Card],
                       bottomDown2: List[Card],
                       bottomUp1: List[Card],
                       bottomUp2: List[Card],
                       trump: Suit,
                       trumpCaller: 1 | 2): Unit =
    // starts with a move
    ???
