enablePlugins(ScalaJSPlugin)

name := "outwatch-sui"

version := "0.1.0"

organization := "wicondev"

scalaVersion := "2.12.1"

jsEnv := PhantomJSEnv().value

libraryDependencies ++= Seq(
  "io.github.outwatch"  %%% "outwatch"  % "0.8.0",
  "io.suzaku"           %%% "diode"     % "1.1.1",
  "org.scalatest"       %%% "scalatest" % "3.0.1"   % Test
)
