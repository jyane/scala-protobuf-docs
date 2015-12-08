import sbt._
import sbt.Keys._
import tut.Plugin._

object build extends Build with NpmCliBase {
  lazy val lintAll = taskKey[Unit]("lint text, html")
  lazy val testAll = taskKey[Unit]("test scala, links")
  lazy val buildWithCheck = taskKey[Unit]("lintAll testAll build")

  // tutでsbtの設定を書く都合上、scalaVersionはわざと指定しないでsbtと同じ2.10.xを使う
  val root = project.in(file(".")).settings(
    tutSettings,
    tutSourceDirectory := srcDir,
    tutTargetDirectory := compiledSrcDir,
    GitBook.settings,
    TextLint.settings,
    LinkTest.settings,
    fullResolvers ~= {_.filterNot(_.name == "jcenter")},
    libraryDependencies <+= sbtDependency,
    resolvers += Classpaths.sbtPluginReleases,
    addSbtPlugin("com.trueaccord.scalapb" % "sbt-scalapb" % "0.5.18"),
    libraryDependencies ++= (
      ("com.github.os72" % "protoc-jar" % "3.0.0-b1") ::
      Nil
    ),
    lintAll := Def.sequential(LinkTest.eslint, TextLint.textlint.toTask("")).value,
    testAll := Def.sequential(compile in Test, LinkTest.linkTest).value,
    buildWithCheck := Def.sequential(lintAll, testAll, GitBook.build)
  )
}