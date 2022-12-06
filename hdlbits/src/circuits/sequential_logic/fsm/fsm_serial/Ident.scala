package circuits.sequential_logic.fsm.fsm_serial

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class SerialIdent extends Component {
  val io = new Bundle {
    val data = in Bool ()
    val done = out Bool ()
  }

  val receivedCount = RegInit(U(0, 3 bits))

  val fsm = new StateMachine {
    val idle: State = makeInstantEntry().whenIsActive {
      when(!io.data) {
        goto(data)
      }
    }
    val invalid = new State {
      whenIsActive {
        when(io.data) {
          goto(idle)
        }
      }
    }
    val success: State = new State {
      whenIsActive {
        when(!io.data) {
          goto(data)
        } otherwise {
          goto(idle)
        }
      }
    }
    val data = new State {
      whenIsActive {
        when(receivedCount === 7) {
          goto(stop)
        }
      }
    }
    val stop = new State {
      whenIsActive {
        when(io.data) {
          goto(success)
        } otherwise {
          goto(invalid)
        }
      }
    }
  }

  when(fsm.isActive(fsm.data)) {
    receivedCount := receivedCount + 1
  } otherwise {
    receivedCount := 0
  }

  io.done := fsm.isActive(fsm.success)
}

object IdentVerilog {
  def main(args: Array[String]) =
    generate("fsm_serial", new SerialIdent, ClockDomainConfig(resetKind = SYNC))
}
