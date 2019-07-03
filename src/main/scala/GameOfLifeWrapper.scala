import chisel3._
import dimensions.Size
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}

class GameOfLifeWrapper(size: Size) extends Module {
  val io = IO(new Bundle{
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

  io.write_enable := false.B
  io.write_data := 0.U
  io.address_to_access := io.starting_address
  io.completed := false.B
}



object GameOfLifeWrapper extends App {
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga/chisel_output")
  Driver.execute(optionsManager, () => new GameOfLifeWrapper(Size(2, 2)))
}