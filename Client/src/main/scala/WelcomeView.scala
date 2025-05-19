import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.control.{Button, TextField}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.VBox

object WelcomeView extends JFXApp3:
  override def start(): Unit =
    val hostField = new TextField {
      id = "hostField"
      promptText = "Server host"
      disable = true
      alignment = Pos.Center
      editable = true
    }
    val portField = new TextField {
      id = "portField"
      promptText = "Server port"
      disable = true
      alignment = Pos.Center
      editable = true
    }
    val playButton = new Button {
      id = "playButton"
      text = "Play"
      alignment = Pos.Center
    }
    val root = new VBox {
      style = "-fx-background-color: Black;"
      children = Seq(hostField, portField, playButton)
      prefHeight = 75
      prefWidth = 75
    }

    stage = new PrimaryStage {
      title = "Welcome"
      scene = new Scene(root, 300, 150)
    }
