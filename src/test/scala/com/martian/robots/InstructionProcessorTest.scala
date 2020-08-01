package com.martian.robots

import org.scalatest.{FunSpec, Matchers}

class InstructionProcessorTest extends FunSpec with Matchers {

  it("handles turn right instruction when facing N by modifying orientation") {
    val robot = Robot(Position(1,1,Orientation.North))

    val instructions = List("R")
    val movedRobot = InstructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.East
  }

  it("handles turn left instruction when facing N by modifying orientation") {
    val robot = Robot(Position(1,1,Orientation.North))

    val instructions = List("L")
    val movedRobot = InstructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.West
  }

  it("handles turn right instruction when facing W by modifying orientation") {
    val robot = Robot(Position(1,1,Orientation.West))

    val instructions = List("R")
    val movedRobot = InstructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.North
  }

  //TODO handle F movement
}
