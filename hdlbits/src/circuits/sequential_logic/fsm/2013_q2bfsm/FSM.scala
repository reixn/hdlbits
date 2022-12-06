package circuits.sequential_logic.fsm.`2013_q2bfsm`

import spinal.core._
import spinal.lib.fsm._
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val x = in Bool ()
    val y = in Bool ()
    val f = out Bool ()
    val g = out Bool ()
  }

  val fsm = new StateMachine {
    val start = new State with EntryPoint {
      whenIsActive {
        goto(x0)
      }
    }
    val x0: State = new State {
      whenIsActive {
        when(io.x) {
          goto(x1)
        }
      }
    }
    val x1: State = new State {
      whenIsActive {
        when(!io.x) {
          goto(x2)
        }
      }
    }
    val x2 = new State {
      whenIsActive {
        when(io.x) {
          goto(y0)
        } otherwise {
          goto(x0)
        }
      }
    }
    val y0 = new State {
      whenIsActive {
        when(io.y) {
          goto(g1)
        } otherwise {
          goto(y1)
        }
      }
    }
    val y1 = new State {
      whenIsActive {
        when(io.y) {
          goto(g1)
        } otherwise {
          goto(g0)
        }
      }
    }
    val g0 = new State
    val g1 = new State
  }

  io.f := fsm.isActive(fsm.start)
  io.g :=
    fsm.isActive(fsm.y0) ||
      fsm.isActive(fsm.y1) ||
      fsm.isActive(fsm.g1)
}

object FSMVerilog {
  def main(arg: Array[String]) =
    generate(
      "2013_q2bfsm",
      new FSM,
      ClockDomainConfig(resetKind = SYNC, resetActiveLevel = LOW)
    )
}
