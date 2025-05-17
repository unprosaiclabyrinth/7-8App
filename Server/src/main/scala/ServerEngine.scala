import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.{ServerSocket, Socket}
import scala.concurrent.{Future, blocking}
import scala.concurrent.ExecutionContext.Implicits.global

object ServerEngine:
  private case class Player(id: Int,
                            socket: Socket,
                            in: ObjectInputStream,
                            out: ObjectOutputStream):
    def send(msg: Message): Unit =
      out.writeObject(msg)
      out.flush()

    def recv: Message =
      blocking(in.readObject.asInstanceOf[Message])

  def start(port: Int, updateGUI: Message => Unit): Unit =
    val server = new ServerSocket(port)
    // log: s"Listening on port $port"

    // accept exactly 2 players
    val p1 = acceptPlayer(1, server)
    p1.send(WaitingFor1ToStart)
    updateGUI(WaitingFor1ToStart)
    val p2 = acceptPlayer(2, server)
    val player: Map[Int, Player] = Map(1 -> p1, 2 -> p2)

    def broadcast(msg: Message): Unit =
      player.values.foreach(_.send(msg))
      updateGUI(msg)

    val trumpCaller = if scala.util.Random.nextBoolean then 1 else 2
    broadcast(CallingTrump(trumpCaller))
    broadcast(player(trumpCaller).recv.asInstanceOf[Trump])

    ???


  private def acceptPlayer(id: Int, server: ServerSocket): Player =
    val sock = blocking(server.accept())
    // log: s"Player $id connected from ${sock.getInetAddress}:${sock.getPort}"

    // create object streams -- OOS first, then flush it, then OIS
    val out = new ObjectOutputStream(sock.getOutputStream)
    out.flush()
    val in = new ObjectInputStream(sock.getInputStream)

    Player(id, sock, in, out)

  private def playGame(): Unit =
    // starts with a move
    ???
