import chisel3._
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}
import neighbourFinder._
import dimensions._

class GameOfLife(size: Size, cellGenerator: Int => Cell, connector: Connector) extends Module {
  
  val io = IO(new Bundle {
    val initialState = Input(Vec(size.rows, Vec(size.columns, Bool())))
    val enable = Input(Bool())
    val currentState = Output(Vec(size.rows, Vec(size.columns, Bool())))
  })

  private val neighbourPositionsOfAllCells = Seq.range(0, size.rows).map(
    row => Seq.range(0, size.columns).map(column => getNeighbours(Position(row, column),size))
  )

  private val cells = Seq.range(0, size.rows).map(
    row => Seq.range(0, size.columns).map(column => Module(cellGenerator(neighbourPositionsOfAllCells(row)(column).length)).io)
  )

  def configureCell(row: Int, col: Int): Unit = {
    val cell = cells(row)(col)
    connector.connect(cell.initialState, io.initialState(row)(col))
    connector.connect(cell.enable, io.enable)
    val neighbours = neighbourPositionsOfAllCells(row)(col)
    for (i <- neighbours.indices) {
      val neighbour = neighbours(i)
      val neighbourCell = cells(neighbour.x)(neighbour.y)
      connector.connect(cell.currentStateOfNeighbours(i), neighbourCell.currentState)
    }
    connector.connect(io.currentState(row)(col), cell.currentState)
  }

  for (row <- 0 until size.rows; column <- 0 until size.columns) {
    configureCell(row, column)
  }
}


object GameOfLife extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga_dir")
  Driver.execute(optionsManager, () => new GameOfLife(Size(-1,1), (n: Int) => new Cell(n), new Connector))
}