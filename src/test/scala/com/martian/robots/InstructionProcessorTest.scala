package com.martian.robots

import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class InstructionProcessorTest extends FunSpec with Matchers with BeforeAndAfterEach {

  var instructionProcessor:InstructionProcessor = _

  override def beforeEach(): Unit = {
    val (xCoordMax, yCoordMax) = (4,3)
    val grid = gridFrom(xCoordMax,yCoordMax)

    instructionProcessor = new InstructionProcessor(grid)
  }

  it("should orient E when facing N and turn right instruction provided") {
    val robot = Robot(Position(1,1,Orientation.North))

    val instructions = List("R")
    val movedRobot = instructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.East
  }

  it("should orient W when facing N and turn left instruction provided ") {
    val robot = Robot(Position(1,1,Orientation.North))

    val instructions = List("L")
    val movedRobot = instructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.West
  }

  it("should orient N when facing W and turn right instruction provided") {
    val robot = Robot(Position(1,1,Orientation.West))

    val instructions = List("R")
    val movedRobot = instructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.North
  }

  it("should modify coords when facing E and forward instruction provided") {
    val robot = Robot(Position(1,1,Orientation.East))

    val instructions = List("F")
    val movedRobot = instructionProcessor.process(instructions, robot)

    movedRobot.posn.x shouldEqual 2
    movedRobot.posn.y shouldEqual 1
    movedRobot.posn.orientation shouldEqual Orientation.East
  }

  //TODO account for loss over grid/edge
  it("") {

  }
}
