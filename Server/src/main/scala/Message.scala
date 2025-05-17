sealed trait Message extends Serializable

case object WaitingFor1ToStart extends Message
case class CallingTrump(caller: Int) extends Message
case class Trump(trump: Suit) extends Message
case class Move(turn: Int, alreadyPlayed: Option[Card], bottomReveal: Option[Card]) extends Message
case class RoundResult(roundWinner: Int) extends Message
case class GameResult(winner: Int) extends Message
case object PlayAgain extends Message
case object Quit extends Message
