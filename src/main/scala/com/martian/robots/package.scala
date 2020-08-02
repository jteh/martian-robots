package com.martian

package object robots {
  val moveCoordsFns = Map(
    (Orientation.North -> ((x:Int,y:Int) => {(x,y+1)})),
    (Orientation.East -> ((x:Int,y:Int) => {(x+1,y)})),
    (Orientation.South -> ((x:Int,y:Int) => {(x,y-1)})),
    (Orientation.West -> ((x:Int,y:Int) => {(x-1,y)}))
  )

  val instructionRegistry = Map[String,Instruction](
    ("L" -> OrientationInstruction("L",-1)),
    ("R" -> OrientationInstruction("R",1)),
    ("F" -> CoordsInstruction("F"))
  )

  def gridFrom(xCoordMax:Int, yCoordMax: Int) = {
    val cells = (0 to yCoordMax)
      .map(yCoord => {
        val row = (0 to xCoordMax)
          .map(xCoord => (xCoord, Cell(false)))
          .toMap
        (yCoord, row)
      })
      .toMap

    Grid(xCoordMax,yCoordMax,cells)
  }
}