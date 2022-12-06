package circuits.sequential_logic.fsm.`2014_q3fsm`

import spinal.core._
import spinal.lib.fsm._
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val s = in Bool ()
    val w = in Bool ()
    val z = out Bool ()
  }

  val count = RegInit(U(0, 2 bits))

  val fsm = new StateMachine {
    val a = makeInstantEntry().whenIsActive {
      when(io.s) {
        goto(b0F)
      }
    }
    val b0F = new State {
      whenIsActive {
        goto(b1)
      }
    }
    val b0S = new State {
      whenIsActive {
        goto(b1)
      }
    }
    val b1 = new State {
      whenIsActive {
        goto(b2)
      }
    }
    val b2: State = new State {
      whenIsActive {
        when(count + io.w.asUInt === 2) {
          goto(b0S)
        } otherwise {
          goto(b0F)
        }
      }
    }
  }

  val isCount =
    fsm.isActive(fsm.b0F) ||
      fsm.isActive(fsm.b0S) ||
      fsm.isActive(fsm.b1)
  when(isCount) {
    count := count + io.w.asUInt
  } otherwise {
    count := 0
  }

  io.z := fsm.isActive(fsm.b0S)
}

object FSMVerilog {
  def main(arg: Array[String]) =
    generate("2014_q3fsm", new FSM, ClockDomainConfig(resetKind = SYNC))
}
