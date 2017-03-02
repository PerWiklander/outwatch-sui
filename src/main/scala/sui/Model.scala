package sui

import diode.{Action, ActionHandler}
import sui.Pages.Page

import scala.scalajs.js

case object Increment extends Action
case object Decrement extends Action
case object Reset     extends Action

case class NavigateTo[R <: Page](
  route: R
) extends Action


case class IncrementCounter(value: Int = 0)

case class RootModel(
  incrementCounter: IncrementCounter  = IncrementCounter(),
  currentPage:      Page              = Pages.Incrementer()
)

object AppCircuit extends ObservableCircuit[RootModel] {
  override protected def initialModel: Model = RootModel()

  val incrementHandler = new ActionHandler(zoomTo(_.incrementCounter.value)) {
    override def handle = {
      case Increment =>
        updated(value + 1)

      case Decrement =>
        updated(value - 1)

      case Reset =>
        updated(0)
    }
  }

  /**
    * A Diode action handler that takes care of application navigation
    */
  val routeHandler = new ActionHandler(zoomTo(_.currentPage)) {
    override def handle = {
      case NavigateTo(page) =>
        if(js.Dynamic.global.location.hash.asInstanceOf[String] != page.hash) {
          js.Dynamic.global.location.hash = page.hash
        }

        updated(page)
    }
  }

  override protected val actionHandler: HandlerFunction =
    foldHandlers(
      incrementHandler,
      routeHandler
    )
}
