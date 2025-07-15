import scalafx.animation.{KeyFrame, Timeline}
import scalafx.event.ActionEvent
import scalafx.scene.control.TextField
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scalafx.util.Duration
import scalafx.Includes.*
import scalafx.geometry.Pos

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

  private val dots: List[Circle] = List.fill(3)(new Circle {
    radius = 10
    fill = Color.Black
  })

  private val dotBox = new HBox {
    id = "dotBox"
    alignment = Pos.Center
    children = dots
    spacing = 50
    prefHeight = 100
  }

  private val animation: Timeline = new Timeline {
    cycleCount = Timeline.Indefinite
    keyFrames = Seq(
      KeyFrame(Duration(0), onFinished = (_: ActionEvent) => setActive(0)),
      KeyFrame(Duration(500), onFinished = (_: ActionEvent) => setActive(1)),
      KeyFrame(Duration(1000), onFinished = (_: ActionEvent) => setActive(2)),
      KeyFrame(Duration(1500), onFinished = (_: ActionEvent) => {})
    )
  }

  def startAnimation(): Unit = animation.play()

  def stopAnimation(): Unit =
    animation.stop()
    dots.foreach(_.fill = Color.Black)

  val root: VBox = new VBox {
    style = "-fx-background-color: Black;"
    children = Seq(topPadding, message, dotBox)
  }

  private def setActive(idx: Int): Unit =
    dots.foreach(_.fill = Color.PeachPuff)
    dots(idx % 3).fill = Color.Tomato
