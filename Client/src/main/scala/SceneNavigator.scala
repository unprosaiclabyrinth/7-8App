import scalafx.stage.Stage
import scalafx.scene.{Scene, Parent}

import scala.compiletime.uninitialized

object SceneNavigator:
  private var theStage: Stage = uninitialized

  def init(stage: Stage): Unit = theStage = stage

  def goTo(root: Parent, width: Int, height: Int): Unit =
    theStage.scene = new Scene(root, width, height)
