package circuits.larger_circuits.review2015_fsmseq

import spinal.core._
import spinal.lib.fsm._
import util.generate._

class FsmSeq extends Component {
  val io = new Bundle {
    val data = in Bool ()
    val start_shifting = out Bool ()
  }

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
          goto(accept)
        } otherwise {
          goto(start)
        }
      }
    }
    val accept = new State
  }

  io.start_shifting := fsm.isActive(fsm.accept)
}

object FsmSeqVerilog {
  def main(arg: Array[String]) =
    generate(
      "review2015_fsmseq",
      new FsmSeq,
      ClockDomainConfig(resetKind = SYNC)
    )
}
