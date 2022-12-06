package circuits.sequential_logic.fsm.m2014_q6

import spinal.core._
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val w = in Bool ()
    val z = out Bool ()
  }

  object State extends SpinalEnum {
    val a, b, c, d, e, f = newElement()
  }

  val state = RegInit(State.a)

  state := state.mux(
    State.a -> (io.w ? State.a | State.b),
    State.b -> (io.w ? State.d | State.c),
    State.c -> (io.w ? State.d | State.e),
    State.d -> (io.w ? State.a | State.f),
    State.e -> (io.w ? State.d | State.e),
    State.f -> (io.w ? State.d | State.c)
  )
  io.z := state === State.e || state === State.f
}

object FSMVerilog {
  def main(arg: Array[String]) =
    generate("m2014_q6", new FSM, ClockDomainConfig(resetKind = SYNC))
}
