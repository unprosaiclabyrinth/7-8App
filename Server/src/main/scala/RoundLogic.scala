class RoundLogic(top1: List[Card],
                 top2: List[Card],
                 bottomDown1: List[Card],
                 bottomDown2: List[Card],
                 bottomUp1: List[Card],
                 bottomUp2: List[Card],
                 trump: Suit):
  require(
    top1.length == 5 && top2.length == 5 && bottomDown1.length == 5
      && bottomDown2.length == 5 && bottomUp1.length == 5 && bottomUp2.length == 5,
    "*** Invalid deal."
  )

  def isValidMove(player: Int, oppMove: Option[Card]): Boolean =
    ???

  def roundWinner(card1: Card, card2: Card): 1 | 2 =
    ???

  def gameWinner: Option[1 | 2] =
    ???
