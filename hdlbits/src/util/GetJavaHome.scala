package util

import java.nio.file._
import java.lang.System
import scala.Console

object javaPath {
  def includeDir() = Some({
    val ret = Paths
      .get(System.getProperty("java.home") + "/include")
      .toRealPath()
      .toString()
    Console.println("[info] add jni include path " + ret)
    ret
  })
}
