import scalafx.application.JFXApp3
import scalafx.geometry.Pos
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}
import com.typesafe.scalalogging.LazyLogging

import java.io.{ObjectInputStream, ObjectOutputStream}
import scala.util.{Failure, Success}

object WelcomeView extends JFXApp3 with LazyLogging:
  override def start(): Unit =
    val hostField = new TextField {
      id = "hostField"
      promptText = "Server host"
      focusTraversable = false
      editable = true
    }

    val portField = new TextField {
      id = "portField"
      promptText = "Server port"
      focusTraversable = false
      editable = true
    }

    val playBtn = new Button {
      id = "playButton"
      text = "Play"
      alignment = Pos.Center
      prefHeight = 140
      prefWidth = 140
    }

     val errorField = new TextField {
      id = "errorField"
      editable = false
      focusTraversable = false
      visible = false
      managed = false
    }

     val playBox = new HBox {
      id = "play"
      alignment = Pos.Center
      children = Seq(playBtn)
      prefHeight = 300
      prefWidth = 300
    }

    val root = new VBox {
      style = "-fx-background-color: Black;"
      children = Seq(hostField, portField, playBox, errorField)
    }

    // when clicked, try to connect
    playBtn.onAction = _ =>
      val host = hostField.text.value
      // parse port or default to -1 so socket will fail
      val port = scala.util.Try(portField.text.value.toInt).getOrElse(-1)

      Player.connectTo(host, port) match
        case Success(sock) =>
          logger.info(s"Connected to ${sock.getInetAddress}:${sock.getPort}.")
          Player.socket = sock
          Player.out = ObjectOutputStream(sock.getOutputStream)
          Player.in = ObjectInputStream(sock.getInputStream)
          Player.recv match
            case WaitingFor1ToStart =>
              stage = Player.setStage("Lobby", LobbyView.root, 400, 400, "/css/lobby.css")
              LobbyView.startAnimation()
              logger.info("Waiting for another player.")
            case _ =>
              logger.info("Unexpected error @ WelcomeView:73!!!")
              System.exit(1)
        case Failure(_) =>
          errorField.text = s"Connection failed."
          errorField.visible = true
          errorField.managed = true

    stage = Player.setStage("Welcome", root, 400, 400, "/css/welcome.css")
