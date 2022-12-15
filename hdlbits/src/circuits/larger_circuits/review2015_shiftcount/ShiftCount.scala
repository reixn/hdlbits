package circuits.larger_circuits.review2015_shiftcount

import spinal.core._
import util.generate._

class ShiftCount extends Component {
  val io = new Bundle {
    val data = in Bool ()
    val shift_ena = in Bool ()
    val count_ena = in Bool ()
    val q = out UInt (4 bits)
  }
  io.q.setAsReg()

  when(io.shift_ena) {
    io.q := B(io.q(2 downto 0), io.data).asUInt
  } elsewhen (io.count_ena) {
    io.q := io.q - 1
  }
}

object ShiftCountVerilog {
  def main(arg: Array[String]) =
    generate("review2015_shiftcount", new ShiftCount)
}
