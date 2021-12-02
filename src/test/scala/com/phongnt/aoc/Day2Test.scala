package com.phongnt.aoc
import cats.effect.{IO, SyncIO}
import munit.CatsEffectSuite
import fs2._

class ExampleSuite extends CatsEffectSuite {

  test("part 2") {
    val input = Stream.emits(List(
      "forward 5",
      "down 5",
      "forward 8",
      "up 3",
      "down 8",
      "forward 2"
    )).covary[IO]
    Day2.part2(input).map(result => assertEquals(result, 900L))
  }
}