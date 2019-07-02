package object dimensions {

  case class Position(x: Int, y: Int)

  case class Size(rows: Int, columns: Int){
    require(rows > 0)
    require(columns > 0)
  }

  case class Bounds(topLeft: Position, bottomRight: Position)

}
