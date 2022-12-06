package circuits.sequential_logic.fsm.ece241_2014_q5a

import spinal.core._
import spinal.lib.fsm._
import util.generate._

class Serial2Comp extends Component {
  val io = new Bundle {
    val x = in Bool ()
    val z = out Bool ()
  }

  val carry = RegInit(True)
  val result = RegInit(False)

  val negX = !io.x
  result := negX ^ carry
  carry := negX & carry

  io.z := result
}

object Serial2CompVerilog {
  def main(arg: Array[String]) = generate("ece241_2014_q5a", new Serial2Comp)
}
