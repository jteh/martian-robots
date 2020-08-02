package com.martian.robots

class InstructionProcessor(grid: Grid) {

  def process(instructions:List[String], robot:Robot) = {
    val movedRobot = instructions.foldLeft(robot)((currentRobot, currentInstr) => {
      if(currentRobot.isLost) {
        currentRobot
      } else {
        val instr = instructionRegistry.getOrElse(currentInstr, throw new NoSuchElementException(s"no instruction found for short name ${currentInstr}"))
        instr match {
          case oi:OrientationInstruction => {
            val currentOrientationIdx = Orientation.fromShortName(currentRobot.posn.orientation.shortName).id
            val orientationIdxShift = oi.orientationIdxShift
            val newOrientation = newOrientationFrom(currentOrientationIdx, orientationIdxShift)

            currentRobot.copy(posn = currentRobot.posn.copy(orientation = newOrientation))
          }
          case _:CoordsInstruction => {
            val currentOrientation = currentRobot.posn.orientation
            val alterCoordFn = alterCoordsFns(currentOrientation)
            val (currentX,currentY) = (currentRobot.posn.x, currentRobot.posn.y)
            val (newX,newY) = alterCoordFn(currentX, currentY)

            if (newY < 0 || newY > grid.yCoordMax || newX < 0 || newX > grid.xCoordMax) {
              if(!grid.get(currentX,currentY).isScentedWithLostRobot) {
                scent(currentX,currentY); currentRobot.copy(isLost = true);
              } else currentRobot
            } else currentRobot.copy(posn = currentRobot.posn.copy(x = newX, y = newY))
          }
        }
      }
    })
    movedRobot
  }

  private def scent(x:Int, y:Int) = {
    val cell = grid.get(x, y)
    grid.set(x,y, cell.copy(isScentedWithLostRobot = true))
  }

  private def newOrientationFrom(currentOrientationIdx:Int, orientationIdxShift:Int) = {
    val shiftedIdx = currentOrientationIdx + orientationIdxShift
    val newIdx = if(shiftedIdx < 0) Orientation.values.size - 1
                 else if (shiftedIdx == Orientation.values.size) 0
                 else shiftedIdx
    Orientation(newIdx)
  }

}
