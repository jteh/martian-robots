package com.martian.robots

object App {
  val exitStr = "exit"

  case class Input(initialPosn:String="",instr:String="",exited:Boolean=false)
  trait InputCollector {
    def collect(input:Input):Input
  }
  object InitPosnInputCollector extends InputCollector {
    override def collect(input: Input): Input = {
      if(!input.exited) {
        println("please enter robots initial coords and orientation separated by a space (or 'exit' to exit):> ")
        val robotInitXY = scala.io.StdIn.readLine
        if(exitStr != robotInitXY) input.copy(initialPosn = robotInitXY)
        else input.copy(exited = true)
      } else input
    }
  }
  object InstrInputCollector extends InputCollector {
    override def collect(input: Input): Input = {
      if(!input.exited) {
        println("please enter robots instruction line (or 'exit' to exit):> ")
        val instrLine = scala.io.StdIn.readLine
        if(exitStr != instrLine) input.copy(instr = instrLine)
        else input.copy(exited = true)
      } else input
    }
  }

  def main(args:Array[String]) = {
    //get input for size of grid
    println("please enter x and y coords separated by a space to initialise grid:> ")
    val gridInitXY = scala.io.StdIn.readLine

    //initialise inputprocessor/grid
    val s = gridInitXY.split(" ")
    val xCoordGridMax = s(0).toInt
    val yCoordGridMax = s(1).toInt
    val instructionProcessor = new InstructionProcessor(gridFrom(xCoordGridMax,yCoordGridMax))

    def collectRobotInputsAndProcess():Unit = {
      val collectedInput = List(InitPosnInputCollector,InstrInputCollector).foldLeft(Input())((inp, collector) =>{
        collector.collect(inp)
      })
      if(!collectedInput.exited) {
        val initPos = collectedInput.initialPosn.split(" ")
        val initX = initPos(0).toInt
        val initY = initPos(1).toInt
        val initOrientation = Orientation.fromShortName(initPos(2))

        val robot = Robot(Position(initX,initY,initOrientation))
        val instructions = collectedInput.instr.map(_.toString).toList

        val movedRobot = instructionProcessor.process(instructions,robot)

        println(s"${movedRobot.posn.x} ${movedRobot.posn.y} ${movedRobot.posn.orientation.shortName} ${if(movedRobot.isLost) "LOST" else ""}")
      }
      if(!collectedInput.exited) collectRobotInputsAndProcess()
    }

    collectRobotInputsAndProcess()
  }
}
