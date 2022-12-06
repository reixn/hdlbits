package circuits.sequential_logic.fsm.m2014_q6b

import spinal.core._
import util.generate._

class FSMy2 extends Component {
  val io = new Bundle {
    val y = in Bits (3 bits)
    val w = in Bool ()
    val y2 = out Bool ()
  }
  io.y2 := io.y.mux(
    B"000" -> False,
    B"001" -> True,
    B"010" -> io.w,
    B"011" -> False,
    B"100" -> io.w,
    B"101" -> True,
    default -> False
  )
}

object FSMy2Verilog {
  def main(arg: Array[String]) =
    generate("m2014_q6b", new FSMy2)
}
