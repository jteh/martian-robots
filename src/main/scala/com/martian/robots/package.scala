package com.martian

import scala.collection.mutable

package object robots {
  val alterCoordsFns = Map(
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
    val cells: mutable.Map[Int, mutable.Map[Int, Cell]] =
      mutable.Map(
        (0 to yCoordMax)
          .map(yCoord => {
            val row = mutable.Map(
                        (0 to xCoordMax)
                          .map(xCoord => (xCoord, Cell(false))):_*
            )
            (yCoord, row)
          }):_*
      )
    Grid(xCoordMax,yCoordMax,cells)
  }
}