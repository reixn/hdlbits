package circuits.sequential_logic.fsm.m2014_q6c

import spinal.core._
import util.generate._

class OnehotLogic extends Component {
  val io = new Bundle {
    val y = in Bits (6 bits)
    val w = in Bool ()
    val y2 = out Bool ()
    val y4 = out Bool ()
  }

  io.y2 := !io.w && io.y(0)
  io.y4 := io.w && (io.y(1) || io.y(2) || io.y(4) || io.y(5))
}

object OnehotLogicVerilog {
  def main(arg: Array[String]) = generate("m2014_q6c", new OnehotLogic)
}
