import chisel3._
import chisel3.util.log2Ceil
import dimensions._
import neighbourFinder._

class GameOfLife(size: Size, cellGenerator: Int => Cell, connector: Connector) extends Module {

  val io = IO(new Bundle {
    val initialState = Input(Vec(size.rows, Vec(size.columns, Bool())))
    val start = Input(Bool())
    val rows = Input(UInt(log2Ceil(size.rows + 1).W))
    val columns = Input(UInt(log2Ceil(size.columns + 1).W))
    val currentState = Output(Vec(size.rows, Vec(size.columns, Bool())))
  })

  private val neighbourPositionsOfAllCells = Seq.range(0, size.rows).map(
    row => Seq.range(0, size.columns).map(column => getNeighbours(Position(row, column), size))
  )

  private val cells = Seq.range(0, size.rows).map(
    row => Seq.range(0, size.columns).map(column => Module(cellGenerator(neighbourPositionsOfAllCells(row)(column).length)))
  )

  def configureCell(row: Int, col: Int): Unit = {
    connector.connect(cells(row)(col).io.start, io.start)
    val cellIO = cells(row)(col).io
    connector.connect(cellIO.initialState, io.initialState(row)(col))
    connector.connect(cellIO.enable, (row.U < io.rows) & (col.U < io.columns))
    val neighbours = neighbourPositionsOfAllCells(row)(col)
    for (i <- neighbours.indices) {
      val neighbour = neighbours(i)
      val neighbourCell = cells(neighbour.x)(neighbour.y).io
      connector.connect(cellIO.currentStateOfNeighbours(i), neighbourCell.currentState)
    }
    connector.connect(io.currentState(row)(col), cellIO.currentState)
  }

  for (row <- 0 until size.rows; column <- 0 until size.columns) {
    configureCell(row, column)
  }
}