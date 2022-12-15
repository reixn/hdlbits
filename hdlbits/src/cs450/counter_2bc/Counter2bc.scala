package cs450.counter_2bc

import spinal.core._
import util.generate._

class Counter2bc extends Component {
  val io = new Bundle {
    val train_valid = in Bool ()
    val train_taken = in Bool ()
    val state = out UInt (2 bits)
  }

  val reg = RegInit(U(1, 2 bits))

  when(io.train_valid) {
    when(io.train_taken) {
      reg := (reg === 3) ? reg | reg + 1
    } otherwise {
      reg := (reg === 0) ? reg | (reg - 1)
    }
  }

  io.state := reg;
}

object Counter2bcVerilog {
  def main(arg: Array[String]) =
    generate(
      "counter_2bc",
      new Counter2bc,
      ClockDomainConfig(resetKind = ASYNC)
    )
}
