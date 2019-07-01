import chisel3._
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}

class GameOfLife(rows: Int, columns: Int) extends Module {
  require(rows > 0)
  require(columns > 0)
  val io = IO(new Bundle {
    val input = Input(Vec(rows, Vec(columns, Bool())))
    val output = Output(Vec(rows, Vec(columns, Bool())))
  })

  val cells = Seq.fill(rows) {
    Seq.fill(columns){
      Module(new NextStateGenerator()).io
    }
  }


  def configureCell(row: Int, col: Int, cell: NextStateGeneratorBundle, input: Vec[Vec[Bool]]):Unit = {
    val neighbours = getNeighbours(Cell(row,col))
    for (i <- neighbours.indices){
      val neighbour = neighbours(i)
      cell.input(i) := input(neighbour.x)(neighbour.y)
    }
    for (i <- neighbours.length until 8){
      cell.input(i) := false.B
    }
    cell.presentState := input(row)(col)
  }
  for (row <- 0 until rows; column <- 0 until columns) {
      configureCell(row,column,cells(row)(column),io.input)
      io.output(row)(column) := cells(row)(column).nextState
    }

  case class Cell(x: Int, y: Int)

  case class Bounds(topLeft: Cell, bottomRight: Cell)

  def cartesian(set1: List[Int], set2: List[Int]): List[Cell] = {
    set1.flatMap(x => set2.map(y => Cell(x,y)))
  }

  private def getNeighboursWithItSelf(x: Int): List[Int] = List(x - 1, x, x + 1)

  private def isSameCell(cell1: Cell, cell2: Cell): Boolean = cell1.x == cell2.x && cell1.y == cell2.y

  private def getPossibleNeighbours(cell: Cell): List[Cell] = {
    val set1 = getNeighboursWithItSelf(cell.x)
    val set2 = getNeighboursWithItSelf(cell.y)
    val possibleNeighbours = cartesian(set1, set2)
    possibleNeighbours.filterNot(possibleNeighbour => isSameCell(cell, possibleNeighbour))
  }

  private def isNumberWithInLimits(number: Int, upperBound: Int, lowerBound: Int): Boolean = {
    number >= upperBound && number <= lowerBound
  }

  private def isRowInLimits(bounds: Bounds, cell: Cell): Boolean = isNumberWithInLimits(cell.x, bounds.topLeft.x, bounds.bottomRight.x)

  private def isColInLimits(bounds: Bounds, cell: Cell): Boolean = isNumberWithInLimits(cell.y, bounds.topLeft.y, bounds.bottomRight.y)

  private def isWithinBound(bounds: Bounds, cell: Cell): Boolean = isRowInLimits(bounds, cell) && isColInLimits(bounds, cell)

  private def getValidNeighbours(bounds: Bounds, cell: Cell): List[Cell] = {
    val possibleNeighbours: List[Cell] = getPossibleNeighbours(cell)
    possibleNeighbours.filter(possibleNeighbour => isWithinBound(bounds, possibleNeighbour))
  }

  private def getNeighbours(cell: Cell): List[Cell] = getValidNeighbours(Bounds(topLeft = Cell(0, 0), bottomRight = Cell(rows-1, columns-1)), cell)
}


object GameOfLife extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga_dir")
  Driver.execute(optionsManager, () => new GameOfLife(2, 2))
}