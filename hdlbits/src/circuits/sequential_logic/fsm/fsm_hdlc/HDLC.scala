package circuits.sequential_logic.fsm.fsm_hdlc

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class HDLC extends Component {
  val io = new Bundle {
    val line = in Bool ()
    val disc, flag, err = out Bool ()
  }

  val count = RegInit(U(0, 3 bits))

  val fsm = new StateMachine {
    val zero = makeInstantEntry().whenIsActive {
      when(io.line) {
        goto(one)
      }
    }
    val zeroDisc = new State {
      whenIsActive {
        when(io.line) {
          goto(one)
        } otherwise {
          goto(zero)
        }
      }
    }
    val zeroFlag = new State {
      whenIsActive {
        when(io.line) {
          goto(one)
        } otherwise {
          goto(zero)
        }
      }
    }
    val one: State = new State {
      whenIsActive {
        switch(count) {
          is(5) {
            when(io.line) {
              goto(err)
            } otherwise {
              goto(zeroFlag)
            }
          }
          is(4) {
            when(!io.line) {
              goto(zeroDisc)
            }
          }
          default {
            when(!io.line) {
              goto(zero)
            }
          }
        }
      }
    }
    val err = new State {
      whenIsActive {
        when(!io.line) {
          goto(zero)
        }
      }
    }
  }

  when(fsm.isActive(fsm.one)) {
    count := count + 1
  } otherwise {
    count := 0
  }

  io.disc := fsm.isActive(fsm.zeroDisc)
  io.flag := fsm.isActive(fsm.zeroFlag)
  io.err := fsm.isActive(fsm.err)
}

object HDLCVerilog {
  def main(arg: Array[String]) =
    generate("fsm_hdlc", new HDLC, ClockDomainConfig(resetKind = SYNC))
}
