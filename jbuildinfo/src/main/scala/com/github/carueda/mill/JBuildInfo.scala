package com.github.carueda.mill

import mill.T
import mill.api.{Logger, PathRef}
import mill.scalalib.JavaModule

/** Adapted from `mill.contrib.buildinfo.BuildInfo`.
  */
trait JBuildInfo extends JavaModule {

  def buildInfoPackageName: Option[String] = None

  def buildInfoClassName: String = "BuildInfo"

  def buildInfoMembers: T[Map[String, String]] = T {
    Map.empty[String, String]
  }

  def generatedBuildInfo: T[(Seq[PathRef], PathRef)] = T {
    val logger: Logger = T.ctx().log
    val members: Map[String, String] = buildInfoMembers()
    if (members.nonEmpty) {
      val outputFile = T.ctx().dest / s"$buildInfoClassName.java"
      val internalMembers =
        members
          .map { case (name, value) =>
            s"""  public static final String get${name.capitalize}() { return "${value}"; }"""
          }
          .mkString("\n")
      logger.debug(
        s"Generating class [${buildInfoPackageName.map(_ + ".").getOrElse("")}${buildInfoClassName}] with [${members.size}] members to [${outputFile}]"
      )
      os.write(
        outputFile,
        buildInfoPackageName
          .map(packageName => s"package ${packageName};\n\n")
          .getOrElse("") +
          s"""|public class $buildInfoClassName {
            |$internalMembers
            |}""".stripMargin
      )
      (Seq(PathRef(outputFile)), PathRef(T.ctx().dest))
    } else {
      logger.debug("No build info member defined, skipping code generation")
      (Seq.empty[PathRef], PathRef(T.ctx().dest))
    }
  }

  override def generatedSources = T {
    val (_, destPathRef) = generatedBuildInfo()
    super.generatedSources() :+ destPathRef
  }
}
