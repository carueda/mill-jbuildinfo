import mill._, scalalib._
import mill.scalalib.publish._
import mill.scalalib.api.Util.scalaNativeBinaryVersion

val millVersions = Seq("0.10.0")
val millBinaryVersions = millVersions.map(scalaNativeBinaryVersion)

def millBinaryVersion(millVersion: String) = scalaNativeBinaryVersion(
  millVersion
)
def millVersion(binaryVersion: String) =
  millVersions.find(v => millBinaryVersion(v) == binaryVersion).get

object jbuildinfo extends Cross[JBuildInfoModule](millBinaryVersions: _*)
class JBuildInfoModule(val millBinaryVersion: String) extends ScalaModule with PublishModule {
  def artifactName = s"jbuildinfo_mill$millBinaryVersion"
  def millSourcePath = super.millSourcePath / os.up
  def scalaVersion = "2.13.8"
  def publishVersion = "0.1.2"

  override def ivyDeps = Agg(
    ivy"com.lihaoyi::mill-scalalib:${millVersion(millBinaryVersion)}"
  )

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
