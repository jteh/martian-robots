package com.martian.robots

object InstructionProcessor {

  def process(instructions:List[String], robot:Robot) = {

    val newPosn = instructions.foldLeft(robot.posn)((updatedPosn, instr) => {

      val currentOrientationIdx = Orientation.fromShortName(updatedPosn.orientation.shortName).id
      val orientationIdxShift = Instruction.fromShortName(instr).orientationIdxShift

      val newOrientation = newOrientationFrom(currentOrientationIdx, orientationIdxShift)

      updatedPosn.copy(orientation = newOrientation)
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
