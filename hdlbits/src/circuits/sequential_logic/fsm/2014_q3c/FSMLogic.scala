package circuits.sequential_logic.fsm.`2014_q3c`

import spinal.core._
import util.generate._

class FSMLogic extends Component {
  val io = new Bundle {
    val x = in Bool ()
    val y = in Bits (3 bits)
    val y0 = out Bool ()
    val z = out Bool ()
  }

  io.y0 := io.y.mux(
    B"100" -> !io.x,
    default -> (io.y(0) ^ io.x)
  )

  io.z := io.y === B"011" || io.y === B"100"
}

object FSMLogicVerilog {
  def main(arg: Array[String]) = generate("2014_q3c", new FSMLogic)
}
