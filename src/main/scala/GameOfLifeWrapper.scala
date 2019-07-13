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
    val rows = Input(UInt(log2Ceil(size.rows).W))
    val columns = Input(UInt(log2Ceil(size.columns).W))
    val write_enable = Output(Bool())
    val write_data = Output(UInt(8.W))
    val address_to_access = Output(UInt(12.W))
    val completed = Output(Bool())
//    val counter = Output(UInt(8.W))
  })

  val total = io.rows * io.columns

  val gameOfLife = Module(new GameOfLife(size, n => new Cell(n), new Connector))

  val initializationCounter = RegInit(0.U(log2Ceil(size.total + 1).W))
  val writeCounter = RegInit(0.U(log2Ceil(size.total + 2).W))

  val initializationCounterEndCount = Reg(UInt(log2Ceil(size.total + 1).W))
  initializationCounterEndCount := total + 1.U
  val writeCounterEndCount = Reg(UInt(log2Ceil(size.total + 2).W))
  writeCounterEndCount := total + 2.U

  when(io.initialize) {
    initializationCounter := Mux(initializationCounter === initializationCounterEndCount, initializationCounter, initializationCounter + 1.U)
  }

  private val initialStates = Reg(Vec(size.rows, Vec(size.columns, Bool())))

//  private val counter = RegInit(0.U(8.W))
//  io.counter := counter
  when(initializationCounter > 0.U && initializationCounter < initializationCounterEndCount) {
    initialStates((initializationCounter - 1.U) / io.columns)((initializationCounter - 1.U) % io.columns) := io.read_data
//    counter := counter + io.read_data
  }

  io.address_to_access := Mux(io.initialize, initializationCounter + io.starting_address, (writeCounter - 1.U) + io.result_address)

  gameOfLife.io.initialState := initialStates
  gameOfLife.io.rows := io.rows
  gameOfLife.io.columns := io.columns

  io.write_data := gameOfLife.io.currentState((writeCounter - 1.U) / io.columns)((writeCounter - 1.U) % io.columns)

  io.write_enable := !io.initialize && writeCounter > 0.U && writeCounter < writeCounterEndCount

  private val prevState = RegNext(io.start)
  private val startTrigger = io.start === true.B && prevState === false.B
  gameOfLife.io.start := startTrigger

  io.completed := Mux(io.initialize, initializationCounter === initializationCounterEndCount, writeCounter === writeCounterEndCount)
  writeCounter := Mux(startTrigger, 0.U, Mux(writeCounter === writeCounterEndCount, writeCounter, writeCounter + 1.U))
//  when(io.write_enable) {
//    counter := counter + io.write_data
//  }
}

object GameOfLifeWrapper extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga/chisel_output")
  Driver.execute(optionsManager, () => new GameOfLifeWrapper(Size(15,18)))
}