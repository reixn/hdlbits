package circuits.sequential_logic.fsm.fsm_ps2

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class Ps2Parser extends Component {
  val io = new Bundle {
    val data = in Bits (8 bits)
    val done = out Bool ()
  }
  val dat3 = io.data(3)
  val fsm = new StateMachine {
    val first: State = makeInstantEntry().whenIsActive {
      when(dat3) {
        goto(second)
      }
    }
    val firstDone: State = new State {
      whenIsActive {
        when(dat3) {
          goto(second)
        } otherwise {
          goto(first)
        }
      }
    }

    val second = new State {
      whenIsActive(goto(third))
    }
    val third = new State {
      whenIsActive(goto(firstDone))
    }
  }

  io.done := fsm.isActive(fsm.firstDone)
}

object Ps2ParserVerilog {
  def main(args: Array[String]) =
    generate("fsm_ps2", new Ps2Parser, ClockDomainConfig(resetKind = SYNC))
}
