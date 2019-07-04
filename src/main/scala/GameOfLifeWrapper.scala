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

  val initializationCounter = RegInit(0.U(log2Ceil(size.total + 1).W))
  val writeCounter = RegInit(0.U(log2Ceil(size.total + 2).W))

  when(io.initialize) {
    initializationCounter := Mux(initializationCounter === (size.total + 1).U, initializationCounter, initializationCounter + 1.U)
  }.otherwise {
    initializationCounter := 0.U
  }

  private val initialStates = Reg(Vec(size.rows, Vec(size.columns, Bool())))

  when(initializationCounter > 0.U && initializationCounter < (size.total + 1).U) {
    initialStates((initializationCounter - 1.U) / size.rows.U)((initializationCounter - 1.U) % size.columns.U) := io.read_data
  }

  io.address_to_access := Mux(io.initialize, initializationCounter + io.starting_address, (writeCounter - 1.U) + io.result_address)

  gameOfLife.io.initialState := initialStates

  io.write_data := gameOfLife.io.currentState((writeCounter - 1.U) / size.rows.U)((writeCounter - 1.U) % size.columns.U)

  io.write_enable := !io.initialize && writeCounter > 0.U && writeCounter <= (size.total + 1).U

  private val prevState = RegNext(io.start)
  private val startTrigger = io.start === true.B && prevState === false.B

  io.completed := writeCounter === (size.total + 2).U
    writeCounter := Mux(writeCounter === (size.total + 2).U , Mux(startTrigger, 0.U,writeCounter), writeCounter + 1.U)
}

object GameOfLifeWrapper extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga/chisel_output")
  Driver.execute(optionsManager, () => new GameOfLifeWrapper(Size(3,3)))
}