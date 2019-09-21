lazy val jbuildinfo = project.in(file("."))
  .settings(
    organization := "com.github.carueda",
    name := "jbuildinfo",
    version := "0.1.1",

    scalaVersion := "2.12.2",

    libraryDependencies ++= {
      val millVersion = "0.5.1"
      Seq(
        "com.lihaoyi" %% "mill-scalalib" % millVersion
      )
    },

    publishMavenStyle := true,
    publishArtifact in Test := false,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    pomIncludeRepository := { _ => false },
    homepage := Some(url("https://github.com/carueda/mill-jbuildinfo")),
    licenses := Seq("Apache 2.0" -> url("https://www.opensource.org/licenses/Apache-2.0")),
    scmInfo := Some(ScmInfo(url("https://github.com/carueda/mill-jbuildinfo"), "scm:git@github.com:carueda/mill-jbuildinfo.git")),
    pomExtra :=
      <developers>
        <developer>
          <id>carueda</id>
          <name>Carlos Rueda</name>
          <url>https://github.com/carueda</url>
        </developer>
      </developers>,

    sonatypeProfileName := "com.github.carueda"
  )
