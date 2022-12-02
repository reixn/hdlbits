// build.sc
import mill._, scalalib._, publish._

object hdlbits extends ScalaModule {
  def scalaVersion = "2.13.10"

  def ivyDeps = Agg(
    ivy"com.github.spinalhdl::spinalhdl-core:1.7.3",
    ivy"com.github.spinalhdl::spinalhdl-lib:1.7.3"
  )
  def scalacPluginIvyDeps = Agg(
    ivy"com.github.spinalhdl::spinalhdl-idsl-plugin:1.6.4"
  )
}
