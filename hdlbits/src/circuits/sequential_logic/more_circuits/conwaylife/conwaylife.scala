/*
 * SpinalHDL
 * Copyright (c) Dolu, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package circuits.sequential_logic.conwaylife

import spinal.core._

//Hardware definition
class Conwaylife extends Component {
  val io = new Bundle {
    val load = in Bool ()
    val data = in Bits (256 bits)
    val q = out(Bits(256 bits))
  }

  def pos(i: Int, j: Int) = i * 16 + j

  val qReg = Reg(Bits(256 bits))

  when(io.load) {
    qReg := io.data
  } otherwise {
    for (i <- 0 to 15) {
      for (j <- 0 to 15) {
        def fix(p: Int) =
          if (p < 0) { 15 }
          else {
            if (p > 15) { 0 }
            else { p }
          }
        val sum = List(
          (i - 1, j - 1),
          (i - 1, j),
          (i - 1, j + 1),
          (i, j - 1),
          (i, j + 1),
          (i + 1, j - 1),
          (i + 1, j),
          (i + 1, j + 1)
        ).map({ case (a, b) => qReg(pos(fix(a), fix(b))).asUInt })
          .foldRight(U(0, 4 bits))({ (a, b) => a + b })

        switch(sum) {
          is(2) {}
          is(3) {
            qReg(pos(i, j)) := True
          }
          default {
            qReg(pos(i, j)) := False
          }
        }
      }
    }
  }

  io.q := qReg
}

//Generate the MyTopLevel's Verilog
object ConwaylifeVerilog {
  def main(args: Array[String]): Unit =
    SpinalSystemVerilog(new Conwaylife)
}
