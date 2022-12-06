package circuits.sequential_logic.fsm.fsm_serialdp

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class Parity extends Component {
  val io = new Bundle {
    val reset, input = in Bool ()
    val odd = out Bool ()
  }
  io.odd.setAsReg()

  when(io.reset) {
    io.odd := False
  } elsewhen (io.input) {
    io.odd := !io.odd
  }
}

class ReceiverParity extends Component {
  val io = new Bundle {
    val line = in Bool ()
    val done = out Bool ()
    val data = out Bits (8 bits)
  }

  val parity = new Parity
  parity.io.input <> io.line

  val count = RegInit(U(0, 4 bits))
  val recvData = Reg(Bits(9 bits))

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
        recvData := B(io.line, recvData(8 downto 1))
        when(count === 8) {
          goto(stop)
        }
      }
    }
    val stop = new State {
      whenIsActive {
        when(io.line) {
          when(parity.io.odd) {
            goto(success)
          } otherwise {
            goto(idle)
          }
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

  parity.io.reset := !fsm.isActive(fsm.data)

  io.done := fsm.isActive(fsm.success)
  io.data := recvData(7 downto 0)
}

object ReceiverDPVerilog {
  def main(args: Array[String]) = generate(
    "fsm_serialdp",
    new ReceiverParity,
    ClockDomainConfig(resetKind = SYNC)
  )
}
