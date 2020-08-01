package com.martian.robots

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

object Instruction extends Enumeration {
  protected case class Val(shortName:String, orientationIdxShift:Int) extends super.Val
  val Left = Val("L",-1)
  val Right = Val("R",1)

  import scala.language.implicitConversions
  implicit def valueToInstructionVal(x: Value): Val = x.asInstanceOf[Val]

  def fromShortName(name:String) = {
    Instruction.values
      .find(_.shortName == name)
      .getOrElse(throw new NoSuchElementException(s"no instruction found for short name ${name}"))
      .asInstanceOf[Val]
  }
}

case class Position(x:Int, y:Int, orientation:Orientation.Value)
case class Robot(posn:Position)
