ThisBuild / organization := "com.phongnt"
ThisBuild / scalaVersion := "3.1.0"

lazy val root = (project in file(".")).settings(
  name := "advent_of_code_2021",
  libraryDependencies ++= Seq(
    // "core" module - IO, IOApp, schedulers
    // This pulls in the kernel and std modules automatically.
    "co.fs2" %% "fs2-io" % "3.2.2",
    "org.typelevel" %% "cats-effect" % "3.3.0",
    // concurrency abstractions and primitives (Concurrent, Sync, Async etc.)
    "org.typelevel" %% "cats-effect-kernel" % "3.3.0",
    // standard "effect" library (Queues, Console, Random etc.)
    "org.typelevel" %% "cats-effect-std" % "3.3.0",
    "org.typelevel" %% "munit-cats-effect-3" % "1.0.6" % Test
  )
)
