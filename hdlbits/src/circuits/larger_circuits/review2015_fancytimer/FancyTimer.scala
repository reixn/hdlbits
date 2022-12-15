package circuits.larger_circuits.review2015_fancytimer

import spinal.core._
import util.generate._
import circuits.larger_circuits.{
  review2015_fsm,
  review2015_shiftcount,
  review2015_count1k
}

class FancyTimer extends Component {
  val io = new Bundle {
    val data = in Bool ()
    val count = out UInt (4 bits)
    val counting = out Bool ()
    val done = out Bool ()
    val ack = in Bool ()
  }

  val fsm = new review2015_fsm.Fsm
  fsm.io.data <> io.data
  fsm.io.counting <> io.counting
  fsm.io.done <> io.done
  fsm.io.ack <> io.ack

  val counter = new Area {
    val cfg = ClockDomainConfig(resetActiveLevel = LOW)
    val counter1kd = new ClockingArea(
      ClockDomain.current.copy(reset = fsm.io.counting, config = cfg)
    ) {
      val c1k = new review2015_count1k.Counter1k
      val max = c1k.io.q === 999
    }

    val shiftCounter = new ClockingArea(
      ClockDomain.current
        .copy(clockEnable = fsm.io.shift_ena || counter1kd.max, config = cfg)
    ) {
      val ctr = new review2015_shiftcount.ShiftCount
      ctr.io.shift_ena <> fsm.io.shift_ena
      ctr.io.count_ena <> fsm.io.counting
      ctr.io.data <> io.data

      val min = ctr.io.q === 0
    }
  }
  fsm.io.done_counting := counter.shiftCounter.min && counter.counter1kd.max

  io.count <> counter.shiftCounter.ctr.io.q
}

object FancyTimerVerilog {
  def main(arg: Array[String]) =
    generate(
      "review2015_fancytimer",
      new FancyTimer,
      ClockDomainConfig(resetKind = SYNC)
    )
}
