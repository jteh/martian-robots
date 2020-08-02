package com.martian.robots

class InstructionProcessor(grid: Grid) {

  def process(instructions:List[String], robot:Robot) = {
    val newPosn = instructions.foldLeft(robot.posn)((currentPosn, instr) => {

      val inst = instructionRegistry.getOrElse(instr, throw new NoSuchElementException(s"no instruction found for short name ${instr}"))
      inst match {
        case oi:OrientationInstruction => {
          val currentOrientationIdx = Orientation.fromShortName(currentPosn.orientation.shortName).id
          val orientationIdxShift = oi.orientationIdxShift
          val newOrientation = newOrientationFrom(currentOrientationIdx, orientationIdxShift)

          currentPosn.copy(orientation = newOrientation)
        }
        case _:CoordsInstruction => {
          val currentOrientation = currentPosn.orientation
          val moveCoordFn = moveCoordsFns(currentOrientation)
          val (newX,newY) = moveCoordFn(currentPosn.x, currentPosn.y)

          currentPosn.copy(x=newX,y=newY)
        }
      }
    })

    robot.copy(posn = newPosn)
  }

  private def newOrientationFrom(currentOrientationIdx:Int, orientationIdxShift:Int) = {
    val shiftedIdx = currentOrientationIdx + orientationIdxShift
    val newIdx = if(shiftedIdx < 0) Orientation.values.size - 1
                 else if (shiftedIdx == Orientation.values.size) 0
                 else shiftedIdx
    Orientation(newIdx)
  }

}
