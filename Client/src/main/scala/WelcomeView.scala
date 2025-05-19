import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.control.{Button, TextField}
import scalafx.geometry.Pos
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}

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
    val playButton = new HBox {
      id = "play"
      alignment = Pos.Center
      children = Seq(
        new Button {
          id = "playButton"
          text = "Play"
          alignment = Pos.Center
          prefHeight = 140
          prefWidth = 140
        }
      )
      prefHeight = 300
      prefWidth = 300
    }
    val root = new VBox {
      style = "-fx-background-color: Black;"
      children = Seq(hostField, portField, playButton)
    }

    stage = new PrimaryStage {
      title = "Welcome"
      scene = new Scene(root, 400, 400) {
        stylesheets.add(getClass.getResource("/css/welcome.css").toExternalForm)
      }
    }
