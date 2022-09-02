import mill._

import mill.scalalib._
import $exec.plugins
import com.github.carueda.mill._

object module extends JavaModule with JBuildInfo {
  override def buildInfoPackageName = Some("module")
  override def buildInfoClassName = "Generated"
  override def buildInfoMembers = Map(
    "foo" -> "bar"
  )
}

def verify() = module.run()
