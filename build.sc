import mill._, scalalib._
import mill.scalalib.publish._

object jbuildinfo extends ScalaModule with PublishModule {

  def scalaVersion = "2.12.2"
  def publishVersion = "0.1.1"

  override def ivyDeps = {
    val millVersion = "0.5.1"
    Agg(
      ivy"com.lihaoyi::mill-scalalib:${millVersion}"
    )
  }

  def pomSettings = PomSettings(
    description = "mill jbuildinfo",
    organization = "com.github.carueda",
    url = "https://github.com/carueda/mill-jbuildinfo",
    licenses = Seq(License.`Apache-2.0`),
    versionControl = VersionControl.github("carueda", "mill-jbuildinfo"),
    developers = Seq(
      Developer("carueda", "Carlos Rueda","https://github.com/carueda")
    )
  )

  override def scalacOptions = Seq(
    "-deprecation", "-feature", "-encoding", "utf8"
  )
}
