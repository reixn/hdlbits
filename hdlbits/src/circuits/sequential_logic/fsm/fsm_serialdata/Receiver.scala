package circuits.sequential_logic.fsm.fsm_serialdata

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class SerialReceiver extends Component {
  val io = new Bundle {
    val line = in Bool ()
    val done = out Bool ()
    val data = out Bits (8 bits)
  }
  io.data.setAsReg()

  val count = RegInit(U(0, 3 bits))

  val fsm = new StateMachine {
    val idle = makeInstantEntry().whenIsActive {
      when(!io.line) {
        goto(data)
      }
    }
    val invalid = new State {
      whenIsActive {
        when(io.line) {
          goto(idle)
        }
      }
    }
    val success = new State {
      whenIsActive {
        when(!io.line) {
          goto(data)
        } otherwise {
          goto(idle)
        }
      }
    }
    val data: State = new State {
      whenIsActive {
        io.data := B(io.line, io.data(7 downto 1))
        when(count === 7) {
          goto(stop)
        }
      }
    }
    val stop = new State {
      whenIsActive {
        when(io.line) {
          goto(success)
        } otherwise {
          goto(invalid)
        }
      }
    }
  }
  when(fsm.isActive(fsm.data)) {
    count := count + 1
  } otherwise {
    count := 0
  }

  io.done := fsm.isActive(fsm.success)
}

object ReceiverVerilog {
  def main(args: Array[String]) = generate(
    "fsm_serialdata",
    new SerialReceiver,
    ClockDomainConfig(resetKind = SYNC)
  )
}
