import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket

object ServerEngine:
  private case class Player(id: Int,
                            socket: Socket,
                            in: ObjectInputStream,
                            out: ObjectOutputStream
                           )

  def start(port: Int, updateGUI: Message => Unit): Unit =
    ???
