package circuits.sequential_logic.fsm.fsm_ps2data

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class Ps2ParserData extends Component {
  val io = new Bundle {
    val data = in Bits (8 bits)
    val done = out Bool ()
    val bytes = out Bits (24 bits)
  }

  val b1, b2, b3 = Reg(Bits(8 bits))

  val in3 = io.data(3)
  val fsm = new StateMachine {
    val first = makeInstantEntry().whenIsActive {
      when(in3) {
        b1 := io.data
        goto(second)
      }
    }
    val firstDone: State = new State {
      whenIsActive {
        when(in3) {
          b1 := io.data
          goto(second)
        } otherwise {
          goto(first)
        }
      }
    }
    val second = new State {
      whenIsActive {
        b2 := io.data
        goto(third)
      }
    }
    val third = new State {
      whenIsActive {
        b3 := io.data
        goto(firstDone)
      }
    }
  }

  io.bytes := B(b1, b2, b3)
  io.done := fsm.isActive(fsm.firstDone)
}

object Ps2ParserVerilog {
  def main(args: Array[String]) =
    generate(
      "fsm_ps2data",
      new Ps2ParserData,
      ClockDomainConfig(resetKind = SYNC)
    )
}
