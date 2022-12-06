package circuits.sequential_logic.fsm.`2013_q2afsm`

import spinal.core._
import spinal.lib.fsm._
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val r = in Bits (3 bits)
    val g = out Bits (3 bits)
  }

  object State extends SpinalEnum {
    val a, b, c, d = newElement()
  }
  val state = RegInit(State.a)

  switch(state) {
    is(State.a) {
      when(io.r(0)) {
        state := State.b
      } elsewhen (io.r(1)) {
        state := State.c
      } elsewhen (io.r(2)) {
        state := State.d
      } otherwise {
        state := State.a
      }
    }
    is(State.b) {
      state := io.r(0) ? State.b | State.a
    }
    is(State.c) {
      state := io.r(1) ? State.c | State.a
    }
    is(State.d) {
      state := io.r(2) ? State.d | State.a
    }
  }

  io.g(0) := state === State.b
  io.g(1) := state === State.c
  io.g(2) := state === State.d
}

object FSMVerilog {
  def main(arg: Array[String]) = generate(
    "2013_q2afsm",
    new FSM,
    ClockDomainConfig(resetKind = SYNC, resetActiveLevel = LOW)
  )
}
