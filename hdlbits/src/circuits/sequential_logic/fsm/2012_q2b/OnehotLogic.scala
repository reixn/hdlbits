package circuits.sequential_logic.fsm.`2012_q2b`

import spinal.core._
import circuits.sequential_logic.fsm.m2014_q6c
import util.generate._

class OnehotLogic extends Component {
  val io = new Bundle {
    val y = in Bits (6 bits)
    val w = in Bool ()
    val y1 = out Bool ()
    val y3 = out Bool ()
  }

  val logic = new m2014_q6c.OnehotLogic
  logic.io.y <> io.y
  logic.io.w <> !io.w
  io.y1 <> logic.io.y2
  io.y3 <> logic.io.y4
}

object OnehotLogicVerilog {
  def main(arg: Array[String]) =
    generate("2012_q2b", new OnehotLogic)
}
