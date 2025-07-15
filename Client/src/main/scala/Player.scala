import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.{Parent, Scene}

import java.io.{ObjectInputStream, ObjectOutputStream}
import java.net.Socket
import scala.compiletime.uninitialized
import scala.util.Try

object Player:
  var socket: Socket = uninitialized
  var out: ObjectOutputStream = uninitialized
  var in: ObjectInputStream = uninitialized

  def connectTo(host: String, port: Int): Try[Socket] =
    Try(new Socket(host, port))

  def setStage(stageTitle: String,
                       root: Parent, w: Double, h: Double,
                       css: String): PrimaryStage =
    new PrimaryStage {
      title = stageTitle
      scene = new Scene(root, w, h) {
        stylesheets.add(getClass.getResource(css).toExternalForm)
      }
    }
