package sui

import diode.Action
import outwatch.Sink
import outwatch.dom._
import rxscalajs.Observable
import sui.components.Link

object Pages {
  sealed trait Page {
    def hash: String
  }

  trait ModelPage[Model] {
    def apply(model: Observable[Model], dispatcher: Sink[Action]): VNode
  }

  case class Incrementer(hash: String = "increment") extends Page with ModelPage[IncrementCounter] {
    override def apply(source: Observable[IncrementCounter], sink: Sink[Action]): VNode =
      div(
        h1("The Mighty Incrementer"),
        div(
          div(child <-- source.map(_.value)),
          button("Increment", click(Increment) --> sink),
          button("Decrement", click(Decrement) --> sink),
          button("Reset",     click(Reset)     --> sink)
        ),

        ul(
          li(
            Link(Pages.HelloWorld(), "Go to The Hello World Page")
          )
        )
      )
  }

  case class HelloWorld(hash: String = "hello") extends Page {
    def apply(): VNode =
      div(
        h1("Hello World!"),
        ul(
          li(
            Link(Pages.Incrementer(), "Go to The Mighty Incrementer")
          )
        )
      )
  }

  case class PageNotFound(hash: String) extends Page {
    def apply(): VNode =
      div(
        h1(s"Page Not Found: $hash")
      )
  }
}
