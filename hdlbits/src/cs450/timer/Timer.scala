package cs450.timer

import spinal.core._
import util.generate._

class Timer extends Component {
  val io = new Bundle {
    val load = in Bool ()
    val data = in UInt (10 bits)
    val tc = out Bool ()
  }

  val count = Reg(UInt(10 bits))

  val zero = count === 0

  when(io.load) {
    count := io.data
  } otherwise {
    count := zero ? count | count - 1
  }

  io.tc := zero
}

object TimerVerilog {
  def main(arg: Array[String]) =
    generate("timer", new Timer)
}
