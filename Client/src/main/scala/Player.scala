import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene

import java.net.Socket
import scala.util.Try

object Player extends JFXApp3:
  override def start(): Unit =
    val welcomeRoot = WelcomeView(sock =>
      println(s"Connected to ${sock.getInetAddress}:${sock.getPort}")
    )

    SceneNavigator.init(new PrimaryStage {
      title = "Welcome"
      scene = new Scene(welcomeRoot, 400, 400) {
        stylesheets.add(getClass.getResource("/css/welcome.css").toExternalForm)
      }
    })

  def connectTo(host: String, port: Int): Try[Socket] =
    Try(new Socket(host, port))
