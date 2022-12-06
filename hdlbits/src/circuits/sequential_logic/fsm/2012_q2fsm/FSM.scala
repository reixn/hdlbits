package circuits.sequential_logic.fsm.`2012_q2fsm`

import spinal.core._
import circuits.sequential_logic.fsm.m2014_q6
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val w = in Bool ()
    val z = out Bool ()
  }

  val fsm = new m2014_q6.FSM
  fsm.io.w <> !io.w
  fsm.io.z <> io.z
}

object FSMVerilog {
  def main(arg: Array[String]) =
    generate("2012_q2fsm", new FSM, ClockDomainConfig(resetKind = SYNC))
}
