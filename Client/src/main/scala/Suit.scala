enum Suit(val initial: Char, val str: Char):
  case Spades extends Suit('S', '♠')
  case Hearts extends Suit('H', '♥')
  case Diamonds extends Suit('D', '♦')
  case Clubs extends Suit('C', '♣')
  case Back extends Suit('B', 'X') // placeholder/backside
