import chisel3._
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}

class GameOfLife(rows: Int, columns: Int, nextStateGenerator: NextStateGenerator) extends Module {
  require(rows > 0)
  require(columns > 0)
  val io = IO(new Bundle {
    val currentState = Input(Vec(rows, Vec(columns, Bool())))
    val nextState = Output(Vec(rows, Vec(columns, Bool())))
  })


  def configureCell(row: Int, col: Int): Unit = {
    val neighbours = getNeighbours(Position(row, col))
    val currentStateOfNeighbour = VecInit(neighbours.map(neighbour => io.currentState(neighbour.x)(neighbour.y)))
    io.nextState(row)(col) := nextStateGenerator.getNextState(currentStateOfNeighbour, io.currentState(row)(col))
  }

  for (row <- 0 until rows; column <- 0 until columns) {
    configureCell(row, column)
  }

  case class Position(x: Int, y: Int)

  case class Bounds(topLeft: Position, bottomRight: Position)

  def cartesian(set1: List[Int], set2: List[Int]): List[Position] = {
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

  private def getNeighbours(cellPosition: Position): List[Position] = getValidNeighbours(Bounds(topLeft = Position(0, 0), bottomRight = Position(rows - 1, columns - 1)), cellPosition)
}


object GameOfLife extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga_dir")
  Driver.execute(optionsManager, () => new GameOfLife(2, 2, new NextStateGenerator))
}

case class Cell(x: Int, y: Int)