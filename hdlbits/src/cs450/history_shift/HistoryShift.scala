package cs450.history_shift

import spinal.core._
import util.generate._

class HistoryShift(n: Int) extends Component {
  val io = new Bundle {
    val predict_valid = in Bool ()
    val predict_taken = in Bool ()
    val predict_history = out Bits (n bits)

    val train_mispredicted = in Bool ()
    val train_taken = in Bool ()
    val train_history = in Bits (n bits)
  }

  val history = RegInit(B(0, n bits))

  when(io.train_mispredicted) {
    history := B(io.train_history(n - 2 downto 0), io.train_taken)
  } elsewhen (io.predict_valid) {
    history := B(history(n - 2 downto 0), io.predict_taken)
  }

  io.predict_history := history
}

object HistoryShiftVerilog {
  def main(arg: Array[String]) =
    generate(
      "history_shift",
      new HistoryShift(32),
      ClockDomainConfig(resetKind = ASYNC)
    )
}
