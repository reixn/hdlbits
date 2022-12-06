package circuits.sequential_logic.fsm.`2014_q3bfsm`

import spinal.core._
import util.generate._

class FSM extends Component {
  val io = new Bundle {
    val x = in Bool ()
    val z = out Bool ()
  }

  val state = RegInit(B(0, 3 bits))
  state := io.x.mux(
    True -> state.mux(
      B"000" -> B"001",
      B"001" -> B"100",
      B"010" -> B"001",
      B"011" -> B"010",
      B"100" -> B"100",
      default -> B"000"
    ),
    False -> state.mux(
      B"000" -> B"000",
      B"001" -> B"001",
      B"010" -> B"010",
      B"011" -> B"001",
      B"100" -> B"011",
      default -> B"000"
    )
  )

  io.z := state === B"011" || state === B"100"
}

object FSMVerilog {
  def main(arg: Array[String]) =
    generate("2014_q3bfsm", new FSM, ClockDomainConfig(resetKind = SYNC))
}
