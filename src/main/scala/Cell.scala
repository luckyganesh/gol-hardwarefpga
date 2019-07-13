import chisel3._

class Cell(noOfNeighbourCells: Int) extends Module {
  val io = IO(new Bundle {
    val initialState = Input(Bool())
    val start = Input(Bool())
    val enable = Input(Bool())
    val currentStateOfNeighbours = Input(Vec(noOfNeighbourCells, Bool()))
    val currentState = Output(Bool())
  })

  private val presentState = RegInit(false.B)
  private val initialized = RegInit(false.B)

  io.currentState := Mux(io.enable, presentState, false.B)

  private def numberOfAliveNeighbours(): UInt = io.currentStateOfNeighbours.count(n => n)

  private def getNextState(): Bool = {
    val numberOfNeighboursAlive = numberOfAliveNeighbours()
    Mux(numberOfNeighboursAlive === 2.U, presentState, numberOfNeighboursAlive === 3.U)
  }

  when(io.start) {
    presentState := Mux(initialized, getNextState(), io.initialState)
    initialized := true.B
  }
}
