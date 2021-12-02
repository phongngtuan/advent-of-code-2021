package com.phongnt.aoc

import fs2._
import fs2.io._
import cats.effect._
import cats.syntax._
import fs2.io.file.Files
import fs2.io.file.Path
import scala.util.matching.Regex

object Day2 extends IOApp.Simple {

  private val path = getClass.getClassLoader.getResource("day2.txt").toURI
  private def input = {
    Files[IO]
      .readAll(Path.fromNioPath(java.nio.file.Paths.get(path)))
      .through(text.utf8.decode)
      .through(text.lines)
  }


  type Aim = Long
  case class Submarine(x: Long, y: Long, aim: Long)
  
  enum Direction:
    case Forward
    case Up
    case Down

  case class Command(direction: Direction, unit: Long)

  def move(sub: Submarine, command: Command): Submarine = {
    command.direction match {
      case Direction.Down => sub.copy(aim = sub.aim + command.unit)
      case Direction.Up => sub.copy(aim = sub.aim - command.unit)
      case Direction.Forward => sub.copy(x = sub.x + command.unit, y = sub.y + sub.aim * command.unit)
    }
  }

  private def parse(line: String): (Long, Long) = {
    val input = line.split(" ").toList
    val magnitude = input(1).toLong
    val direction = input(0)

    direction match {
      case "forward" => (magnitude, 0)
      case "down" => (0, magnitude)
      case _ => (0, -magnitude)
    }
  }

  private def parseCommand(line: String): Command = {
    val input = line.split(" ").toList
    val magnitude = input(1).toLong
    val direction = input(0) match {
      case "forward" => Direction.Forward
      case "down" => Direction.Down
      case _ => Direction.Up
    }
    Command(direction, magnitude)
  }

  def part1(input: Stream[IO, String]): IO[Long] = {
    input
    .map(parse)
    .foldMonoid
    .map(_ * _)
    .compile
    .lastOrError
  }

  def part2(input: Stream[IO, String]): IO[Long] = {
    input
    .map(parseCommand)
    .fold(Submarine(0, 0, 0))(move)
    .map(sub => sub.x * sub.y)
    .compile
    .lastOrError
  }
  def run: IO[Unit] = {
    val part1 = input
    .map(parse)
    .foldMonoid
    .map(_ * _)
    .printlns
    .compile
    .drain

    val part2_result = part2(input).flatMap(IO.println)
    part1 >> part2_result
  }
}
