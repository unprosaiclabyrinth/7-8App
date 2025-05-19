import java.io.InputStream

import Rank.*
import Suit.*

class Card(val rank: Rank, val suit: Suit):
  val image: InputStream = getClass.getResourceAsStream(s"/${rank.value}${suit.initial}.jpeg")
  
  override def toString: String = rank.str + suit.str

case object FaceDown extends Card(Zero, Back)
