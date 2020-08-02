package com.martian.robots

import org.scalatest.{BeforeAndAfterEach, FunSpec, Matchers}

class InstructionProcessorTest extends FunSpec with Matchers with BeforeAndAfterEach {

  var instructionProcessor:InstructionProcessor = _
  var grid:Grid = _
  val (xCoordMax, yCoordMax) = (5,3)

  override def beforeEach(): Unit = {
    grid = gridFrom(xCoordMax,yCoordMax)
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

  describe("moving off grid") {

    it("should mark robot as lost when it moves south off grid") {
      val robot = Robot(Position(0,0,Orientation.South))

      val instructions = List("F")
      val movedRobot = instructionProcessor.process(instructions, robot)

      movedRobot.isLost shouldEqual true

      movedRobot.posn.x shouldEqual 0
      movedRobot.posn.y shouldEqual 0
      movedRobot.posn.orientation shouldEqual Orientation.South

      grid.get(0,0).isScentedWithLostRobot shouldEqual true
    }

    it("should mark robot as lost when it moves north off grid") {
      val robot = Robot(Position(0,3,Orientation.North))

      val instructions = List("F")
      val movedRobot = instructionProcessor.process(instructions, robot)

      movedRobot.isLost shouldEqual true

      movedRobot.posn.x shouldEqual 0
      movedRobot.posn.y shouldEqual 3
      movedRobot.posn.orientation shouldEqual Orientation.North

      grid.get(0,3).isScentedWithLostRobot shouldEqual true
    }

    it("should mark robot as lost when it moves west off grid") {
      val robot = Robot(Position(0,3,Orientation.West))

      val instructions = List("F")
      val movedRobot = instructionProcessor.process(instructions, robot)

      movedRobot.isLost shouldEqual true

      movedRobot.posn.x shouldEqual 0
      movedRobot.posn.y shouldEqual 3
      movedRobot.posn.orientation shouldEqual Orientation.West

      grid.get(0,3).isScentedWithLostRobot shouldEqual true
    }

    it("should mark robot as lost when it moves east off grid") {
      val robot = Robot(Position(5,3,Orientation.East))

      val instructions = List("F")
      val movedRobot = instructionProcessor.process(instructions, robot)

      movedRobot.isLost shouldEqual true

      movedRobot.posn.x shouldEqual 5
      movedRobot.posn.y shouldEqual 3
      movedRobot.posn.orientation shouldEqual Orientation.East

      grid.get(5,3).isScentedWithLostRobot shouldEqual true
    }
  }

  it("should ignore F instruction when attempting to move off grid from a scented grid cell") {
    val robot = Robot(Position(0,0, Orientation.South))
    val ip = new InstructionProcessor(gridWithScentedCell(0,0))

    val movedRobot = ip.process(List("F","L","F"),robot)

    movedRobot.isLost shouldEqual false
    movedRobot.posn.orientation shouldEqual Orientation.East
    movedRobot.posn.x shouldEqual 1
    movedRobot.posn.y shouldEqual 0
  }

  it("should handle multiple sequentially robot instructions") {
    //robot 1's turn
    val robot1 = Robot(Position(1,1,Orientation.East))
    val instructions1 = List("R","F","R","F","R","F","R","F")

    val robot1NewPosition = instructionProcessor.process(instructions1, robot1)

    robot1NewPosition.posn.x shouldEqual 1
    robot1NewPosition.posn.y shouldEqual 1
    robot1NewPosition.posn.orientation shouldEqual Orientation.East
    robot1NewPosition.isLost shouldEqual false

    //robot 2's turn
    val robot2 = Robot(Position(3,2,Orientation.North))
    val instructions2 = List("F","R","R","F","L","L","F","F","R","R","F","L","L")

    val robot2NewPosition = instructionProcessor.process(instructions2, robot2)

    robot2NewPosition.posn.x shouldEqual 3
    robot2NewPosition.posn.y shouldEqual 3
    robot2NewPosition.posn.orientation shouldEqual Orientation.North
    robot2NewPosition.isLost shouldEqual true

    //robot 3's turn
    val robot3 = Robot(Position(0,3,Orientation.West))
    val instructions3 = List("L","L","F","F","F","L","F","L","F","L")

    val robot3NewPosition = instructionProcessor.process(instructions3, robot3)

    robot3NewPosition.posn.x shouldEqual 2
    robot3NewPosition.posn.y shouldEqual 3
    robot3NewPosition.posn.orientation shouldEqual Orientation.South
    robot3NewPosition.isLost shouldEqual false
  }

  private def gridWithScentedCell(xCoordToScent:Int,yCoordToScent:Int) = {
    grid.set(
      xCoordToScent,yCoordToScent,
      grid.get(xCoordToScent,yCoordToScent).copy(isScentedWithLostRobot = true)
    )
    grid
  }
}
