package util

import spinal.core._

object generate {
  def generate[T <: Component](
      name: String,
      component: => T,
      clockDomainConfig: ClockDomainConfig = ClockDomainConfig()
  ): Unit =
    SpinalConfig(
      mode = SystemVerilog,
      defaultConfigForClockDomains = clockDomainConfig,
      targetDirectory = "rtl/" + name
    ).generate(component)
}
