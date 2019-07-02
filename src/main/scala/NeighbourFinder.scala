import dimensions._

package object neighbourFinder {

  private def cartesian(set1: List[Int], set2: List[Int]): List[Position] = {
    set1.flatMap(x => set2.map(y => Position(x, y)))
  }

  private def getNeighboursWithItSelf(x: Int): List[Int] = List(x - 1, x, x + 1)

  private def isSameCell(cell1: Position, cell2: Position): Boolean = cell1.x == cell2.x && cell1.y == cell2.y

  private def getPossibleNeighbours(cell: Position): List[Position] = {
    val set1 = getNeighboursWithItSelf(cell.x)
    val set2 = getNeighboursWithItSelf(cell.y)
    val possibleNeighbours = cartesian(set1, set2)
    possibleNeighbours.filterNot(possibleNeighbour => isSameCell(cell, possibleNeighbour))
  }

  private def isNumberWithInLimits(number: Int, upperBound: Int, lowerBound: Int): Boolean = {
    number >= upperBound && number <= lowerBound
  }

  private def isRowInLimits(bounds: Bounds, cell: Position): Boolean = isNumberWithInLimits(cell.x, bounds.topLeft.x, bounds.bottomRight.x)

  private def isColInLimits(bounds: Bounds, cell: Position): Boolean = isNumberWithInLimits(cell.y, bounds.topLeft.y, bounds.bottomRight.y)

  private def isWithinBound(bounds: Bounds, cell: Position): Boolean = isRowInLimits(bounds, cell) && isColInLimits(bounds, cell)

  private def getValidNeighbours(bounds: Bounds, cell: Position): List[Position] = {
    val possibleNeighbours: List[Position] = getPossibleNeighbours(cell)
    possibleNeighbours.filter(possibleNeighbour => isWithinBound(bounds, possibleNeighbour))
  }

  def getNeighbours(cellPosition: Position, size: Size): List[Position] = getValidNeighbours(Bounds(topLeft = Position(0, 0), bottomRight = Position(size.rows-1,size.columns-1)), cellPosition)
}
