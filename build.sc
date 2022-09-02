import mill._, scalalib._
import mill.scalalib.publish._
import mill.scalalib.api.Util.scalaNativeBinaryVersion
import $ivy.`de.tototec::de.tobiasroeser.mill.integrationtest::0.6.1`
import de.tobiasroeser.mill.integrationtest._

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
  def publishVersion = "0.2.1"

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
      Developer("carueda", "Carlos Rueda", "https://github.com/carueda"),
      Developer("lolgab", "Lorenzo Gabriele", "https://github.com/lolgab")
    )
  )

  override def scalacOptions = Seq(
    "-deprecation", "-feature", "-encoding", "utf8"
  )
}

object itest extends Cross[itestCross]("0.10.0", "0.10.7")
class itestCross(millVersion: String) extends MillIntegrationTestModule {
  override def millSourcePath = super.millSourcePath / os.up
  def millTestVersion = millVersion
  def pluginsUnderTest = Seq(
    jbuildinfo(millBinaryVersion(millVersion))
  )
  def testBase = millSourcePath / "src"
}
