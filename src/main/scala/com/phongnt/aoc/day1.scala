package com.phongnt.aoc

import fs2._
import fs2.io._
import cats.effect._
import cats.syntax._
import fs2.io.file.Files
import fs2.io.file.Path

object Day1 extends IOApp.Simple {

  private val path = getClass.getClassLoader.getResource("day1.txt").toURI
  private def input = {
    Files[IO]
      .readAll(Path.fromNioPath(java.nio.file.Paths.get(path)))
      .through(text.utf8.decode)
      .through(text.lines)
      .map(_.toLong)
  }

  def run: IO[Unit] = {
    val part1 =
      input
        .zipWithPrevious
        .map { (prev, curr) =>
          prev.map(_ < curr)
        }
        .foldMap(o => o.map(lessThan => if (lessThan) 1 else 0).getOrElse(0))
        .printlns
        .compile
        .drain

    val part2 =
      input
      .sliding(3)
      .filter(_.size == 3)
      .map(_.toList.sum)
      .zipWithPrevious
      .map { (prev, curr) =>
        prev.map(_ < curr)
      }
      .foldMap(o => o.map(lessThan => if (lessThan) 1 else 0).getOrElse(0))
      .printlns
      .compile
      .drain

    part1 >> part2
  }
}
