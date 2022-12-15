package cs450.gshare

import spinal.core._
import util.generate._
import cs450.history_shift
import cs450.counter_2bc

class GShare extends Component {
  val io = new Bundle {
    val predict_valid = in Bool ()
    val predict_taken = out Bool ()
    val predict_history = out Bits (7 bits)
    val predict_pc = in Bits (7 bits)

    val train_valid = in Bool ()
    val train_taken = in Bool ()
    val train_mispredicted = in Bool ()
    val train_history = in Bits (7 bits)
    val train_pc = in Bits (7 bits)
  }

  val pht = Array.fill(128)(new counter_2bc.Counter2bc)
  pht.map(e => e.io.train_taken <> io.train_taken)

  val predict = io.predict_valid.mux(
    True -> (io.predict_pc ^ io.predict_history)
      .muxList(
        Seq
          .range(0, 128)
          .map(i => (i, pht(i).io.state(1)))
      ),
    False -> False
  )
  io.predict_taken := predict

  val train_hash = io.train_pc ^ io.train_history
  for (i <- 0 to 127) {
    pht(i).io.train_valid := io.train_valid && i === train_hash
  }

  val history = new history_shift.HistoryShift(7)
  history.io.predict_valid := io.predict_valid
  history.io.predict_taken := predict
  history.io.predict_history <> io.predict_history
  history.io.train_mispredicted := io.train_mispredicted && io.train_valid
  history.io.train_history <> io.train_history
  history.io.train_taken <> io.train_taken
}

object GShareVerilog {
  def main(arg: Array[String]) =
    generate("gshare", new GShare, ClockDomainConfig(resetKind = ASYNC))
}
