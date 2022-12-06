package circuits.sequential_logic.fsm.ece241_2013_q8

import util.generate._
import spinal.core._
import spinal.lib.fsm._

class Recognize101 extends Component {
  val io = new Bundle {
    val x = in Bool ()
    val z = out Bool ()
  }

  val fsm = new StateMachine {
    val has0 = makeInstantEntry().whenIsActive {
      when(io.x) {
        goto(has1)
      }
    }
    val has1 = new State {
      whenIsActive {
        when(!io.x) {
          goto(has10)
        }
      }
    }
    val has10: State = new State {
      whenIsActive {
        when(io.x) {
          goto(has1)
        } otherwise {
          goto(has0)
        }
      }
    }
  }

  io.z := fsm.isActive(fsm.has10) && io.x
}

object Reco101Verilog {
  def main(arg: Array[String]) = generate(
    "ece241_2013_q8",
    new Recognize101,
    ClockDomainConfig(resetActiveLevel = LOW)
  )
}
