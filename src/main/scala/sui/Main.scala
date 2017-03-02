package sui

import outwatch.dom._

import scala.scalajs.js

object Main extends js.JSApp {

  override def main(): Unit = {

    val sink   = AppCircuit.sink
    val source = AppCircuit.source

    def handleHashChange() = {
      val location: js.Array[String] = js.Dynamic.global.location.hash.split("/").asInstanceOf[js.Array[String]]
      val hash = location(0).replace("#", "")


           if (hash == Pages.HelloWorld().hash || hash == "")   AppCircuit.dispatch(NavigateTo(Pages.HelloWorld()))
      else if (hash == Pages.Incrementer().hash)                AppCircuit.dispatch(NavigateTo(Pages.Incrementer()))
      else                                                      AppCircuit.dispatch(NavigateTo(Pages.PageNotFound(hash)))
    }

    js.Dynamic.global.addEventListener("hashchange", handleHashChange _, false)

    handleHashChange()

    OutWatch.render(
      "#app",
      div(child <-- source.map(model => model.currentPage match {
        case p: Pages.Incrementer  => p(source.map(_.incrementCounter), sink)
        case p: Pages.HelloWorld   => p()
        case p: Pages.PageNotFound => p()
      }))
    )
  }
}
