package circuits.larger_circuits.review2015_fsm

import spinal.core._
import spinal.lib.fsm._
import util.generate._
import spinal.lib.fsm.StateMachine

class Fsm extends Component {
  val io = new Bundle {
    val data = in Bool ()
    val shift_ena = out Bool ()
    val counting = out Bool ()
    val done_counting = in Bool ()
    val done = out Bool ()
    val ack = in Bool ()
  }

  val shiftCount = RegInit(U(3))
  val fsm = new StateMachine {
    val start: State = makeInstantEntry().whenIsActive {
      when(io.data) {
        goto(s1)
      }
    }
    val s1 = new State {
      whenIsActive {
        when(io.data) {
          goto(s2)
        } otherwise {
          goto(start)
        }
      }
    }
    val s2 = new State {
      whenIsActive {
        when(!io.data) {
          goto(s3)
        }
      }
    }
    val s3 = new State {
      whenIsActive {
        when(io.data) {
          goto(shift)
        } otherwise {
          goto(start)
        }
      }
    }
    val shift = new State {
      whenIsActive {
        when(shiftCount === 0) {
          goto(count)
        }
      }
    }
    val count = new State {
      whenIsActive {
        when(io.done_counting) {
          goto(done)
        }
      }
    }
    val done = new State {
      whenIsActive {
        when(io.ack) {
          goto(start)
        }
      }
    }
  }

  when(fsm.isActive(fsm.shift)) {
    shiftCount := shiftCount - 1
  } otherwise {
    shiftCount := 3
  }
  io.shift_ena := fsm.isActive(fsm.shift)

  io.counting := fsm.isActive(fsm.count)

  io.done := fsm.isActive(fsm.done)
}

object FsmVerilog {
  def main(arg: Array[String]) =
    generate("review2015_fsm", new Fsm, ClockDomainConfig(resetKind = SYNC))
}
