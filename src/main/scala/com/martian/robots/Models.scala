package com.martian.robots

import scala.collection.mutable

object Orientation extends Enumeration {
  protected case class Val(shortName: String) extends super.Val
  //scala enum vals are indexed by order of declaration
  val North = Val("N") //0
  val East = Val("E")  //1
  val South = Val("S") //2
  val West = Val("W")  //3

  import scala.language.implicitConversions
  implicit def valueToOrientationVal(x: Value): Val = x.asInstanceOf[Val]

  def fromShortName(name:String) = {
    Orientation.values
      .find(e => {e.shortName == name})
      .getOrElse(throw new NoSuchElementException(s"no orientation found for short name $name"))
      .asInstanceOf[Val]
  }
}

sealed trait Instruction {def shortName:String}
case class OrientationInstruction(shortName:String, orientationIdxShift:Int) extends Instruction
case class CoordsInstruction(shortName:String) extends Instruction

case class Position(x:Int, y:Int, orientation:Orientation.Value)
case class Robot(posn:Position, isLost:Boolean = false)

case class Cell(isScentedWithLostRobot:Boolean)
case class Grid(xCoordMax:Int, yCoordMax:Int, cells:mutable.Map[Int,mutable.Map[Int,Cell]]) {
  def get(x:Int,y:Int) = cells(y)(x)
  def set(x:Int,y:Int, cell:Cell) = {
    cells(y).+=((x,cell));()
  }
}
