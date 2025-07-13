import scalafx.geometry.Pos
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}

import java.net.Socket
import scala.util.{Failure, Success}

object WelcomeView:
  private val hostField = new TextField {
    id = "hostField"
    promptText = "Server host"
    focusTraversable = false
    editable = true
  }

  private val portField = new TextField {
    id = "portField"
    promptText = "Server port"
    focusTraversable = false
    editable = true
  }

  private val playBtn = new Button {
    id = "playButton"
    text = "Play"
    alignment = Pos.Center
    prefHeight = 140
    prefWidth = 140
  }

  private val errorField = new TextField {
    id = "errorField"
    editable = false
    focusTraversable = false
    visible = false
    managed = false
  }

  private val playBox = new HBox {
    id = "play"
    alignment = Pos.Center
    children = Seq(playBtn)
    prefHeight = 300
    prefWidth = 300
  }

  def apply(onConnect: Socket => Unit): VBox =
    // when clicked, try to connect
    playBtn.onAction = _ =>
      val host = hostField.text.value
      // parse port or default to -1 so socket will fail
      val port = scala.util.Try(portField.text.value.toInt).getOrElse(-1)

      Player.connectTo(host, port) match
        case Success(sock) => onConnect(sock)
        case Failure(_) =>
          errorField.text = s"Connection failed."
          errorField.visible = true
          errorField.managed = true

    new VBox {
      style = "-fx-background-color: Black;"
      children = Seq(hostField, portField, playBox, errorField)
    }
