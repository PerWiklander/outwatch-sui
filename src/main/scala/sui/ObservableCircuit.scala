package sui

import diode.{Action, Circuit}
import outwatch.Sink
import rxscalajs.{Observable, Observer}


/**
  * A Diode circuit that has helpers for OutWatch integration
  *
  * @tparam M The type of the root model
  */
trait ObservableCircuit[M <: AnyRef] extends Circuit[M] {
  type Model = M

  /**
    * The action sink is used to send actions from OutWatch components to the Diode circuit.
    */
  val sink: Sink[Action] = Sink.create[Action](dispatch)

  /**
    * The model source is used to consume model changes from the Diode circuit in OutWatch components.
    */
  val source: Observable[Model] = Observable.create((observer: Observer[Model]) => {
    subscribe(zoom(identity))(cursor => observer.next(cursor()))
  }).startWith(initialModel)
}
