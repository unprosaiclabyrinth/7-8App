import scalafx.scene.control.TextField
import scalafx.scene.layout.{HBox, VBox}

object LobbyView:
  private val topPadding: HBox = new HBox {
    id = "topPadding"
    prefHeight = 50
  }

  private val message: TextField = new TextField {
    id = "message"
    editable = false
    text = "Waiting for another player..."
    focusTraversable = false
  }

  def apply(): VBox =
    new VBox {
      style = "-fx-background-color: Black;"
      children = Seq(topPadding, message)
    }
