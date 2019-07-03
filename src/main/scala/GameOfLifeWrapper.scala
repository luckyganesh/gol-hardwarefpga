import chisel3._
import chisel3.util.log2Ceil
import dimensions.Size
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}

class GameOfLifeWrapper(size: Size) extends Module {
  val io = IO(new Bundle {
    val starting_address = Input(UInt(12.W))
    val result_address = Input(UInt(12.W))
    val initialize = Input(Bool())
    val start = Input(Bool())
    val read_data = Input(UInt(8.W))
    val write_enable = Output(Bool())
    val write_data = Output(UInt(8.W))
    val address_to_access = Output(UInt(12.W))
    val completed = Output(Bool())
  })

  val gameOfLife = Module(new GameOfLife(size, n => new Cell(n), new Connector))

  gameOfLife.io.initialize := io.initialize
  gameOfLife.io.start := io.start.asClock()

  val initializationCounter = RegInit(0.U(log2Ceil(size.total).W))
  val writeCounter = RegInit(0.U(log2Ceil(size.total).W))

  when(io.initialize) {
    initializationCounter := Mux(initializationCounter === size.total.U, initializationCounter, initializationCounter + 1.U)
  }

  io.address_to_access := Mux(io.initialize, initializationCounter + io.starting_address, writeCounter + io.result_address)

  private val initialStates = Reg(Vec(size.rows, Vec(size.columns, Bool())))

  initialStates(initializationCounter / size.rows.U)(initializationCounter % size.columns.U) := io.read_data

  gameOfLife.io.initialState := initialStates

  io.write_data := gameOfLife.io.currentState((writeCounter - 1.U) / size.rows.U)(writeCounter % size.columns.U)

  io.write_enable := writeCounter > 0.U && writeCounter <= (size.total + 1).U

  private val prevState = RegNext(io.start)
  private val startTrigger = io.start === true.B && prevState === false.B

  io.completed := writeCounter === (size.total + 1).U
  when(true.B) {
    writeCounter := Mux(writeCounter === (size.total + 1).U, Mux(startTrigger, 0.U, (size.total + 1).U), writeCounter + 1.U)
  }
}

object GameOfLifeWrapper extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga/chisel_output")
  Driver.execute(optionsManager, () => new GameOfLifeWrapper(Size(3, 3)))
}