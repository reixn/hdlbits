package circuits.larger_circuits.review2015_count1k

import spinal.core._
import util.generate._

class Counter1k extends Component {
  val io = new Bundle {
    val q = out UInt (10 bits)
  }

  val counter = RegInit(U(0, 10 bits))
  when(counter === 999) {
    counter := 0
  } otherwise {
    counter := counter + 1
  }

  io.q := counter
}

object Counter1kVerilog {
  def main(arg: Array[String]) =
    generate(
      "review2015_count1k",
      new Counter1k,
      ClockDomainConfig(resetKind = SYNC)
    )
}
