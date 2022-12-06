package circuits.sequential_logic.fsm.lemmings3

import spinal.core._
import spinal.lib._
import util.generate._
import spinal.lib.fsm._

class Lemmings3 extends Component {
  val io = new Bundle {
    val bump_left, bump_right, ground, dig = in Bool ()
    val walk_left, walk_right, aaah, digging = out Bool ()
  }

  val fsm = new StateMachine {
    setEncoding(binaryOneHot)

    val walk_left: State = makeInstantEntry().whenIsActive {
      when(io.ground) {
        when(io.dig) {
          goto(dig_left)
        } elsewhen (io.bump_left) {
          goto(walk_right)
        }
      } otherwise {
        goto(fall_left)
      }
    }

    val walk_right: State = new State {
      whenIsActive {
        when(io.ground) {
          when(io.dig) {
            goto(dig_right)
          } elsewhen (io.bump_right) {
            goto(walk_left)
          }
        } otherwise {
          goto(fall_right)
        }
      }
    }
    val dig_left = new State {
      whenIsActive {
        when(!io.ground) {
          goto(fall_left)
        }
      }
    }
    val dig_right = new State {
      whenIsActive {
        when(!io.ground) {
          goto(fall_right)
        }
      }
    }
    val fall_left = new State {
      whenIsActive {
        when(io.ground) {
          goto(walk_left)
        }
      }
    }
    val fall_right = new State {
      whenIsActive {
        when(io.ground) {
          goto(walk_right)
        }
      }
    }

  }
  io.walk_left := fsm.isActive(fsm.walk_left)
  io.walk_right := fsm.isActive(fsm.walk_right)
  io.aaah := fsm.isActive(fsm.fall_left) | fsm.isActive(fsm.fall_right)
  io.digging := fsm.isActive(fsm.dig_left) | fsm.isActive(fsm.dig_right)
}

object LemmingsVerilog {
  def main(args: Array[String]) = generate("lemmings3", new Lemmings3)
}
