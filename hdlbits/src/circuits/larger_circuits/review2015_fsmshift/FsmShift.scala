package circuits.larger_circuits.review2015_fsmshift

import spinal.core._
import util.generate._

class FsmShift extends Component {
  val io = new Bundle {
    val shift_ena = out Bool ()
  }

  val reg = RegInit(U(4))
  when(reg =/= 0) {
    reg := reg - 1
    io.shift_ena := True
  } otherwise {
    io.shift_ena := False
  }
}

object FsmShiftVerilog {
  def main(arg: Array[String]) =
    generate(
      "review2015_fsmshift",
      new FsmShift,
      ClockDomainConfig(resetKind = SYNC)
    )
}
