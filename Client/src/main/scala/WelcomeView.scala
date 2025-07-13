import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.{HBox, VBox}
import java.net.Socket

object WelcomeView extends JFXApp3:
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

    // when clicked, try to connect
    playBtn.onAction = _ =>
      val host = hostField.text.value
      // parse port or default to -1 so socket will fail
      val port = scala.util.Try(portField.text.value.toInt).getOrElse(-1)

      try {
        val sock = new Socket(host, port)
        println(s"Connected to $host:$port") // or do something with sock
        sock.close()
      } catch {
        case e: Throwable =>
          errorField.text = s"Connection failed."
          errorField.visible = true
          errorField.managed = true
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

    stage = new PrimaryStage {
      title = "Welcome"
      scene = new Scene(root, 400, 400) {
        stylesheets.add(getClass.getResource("/css/welcome.css").toExternalForm)
      }
    }
