import chisel3._
import firrtl.{ExecutionOptionsManager, HasFirrtlOptions}

class NextStateGenerator extends Module {
  val io = IO(new NextStateGeneratorBundle)

  private def calculate(vec: Vec[Bool]): UInt = vec.count(x => x)

  private def getNextState(numberOfNeighboursAlive:UInt,presentState:Bool): Bool = {
    Mux(numberOfNeighboursAlive === 2.U, presentState,numberOfNeighboursAlive === 3.U)
  }

  io.nextState := getNextState(calculate(io.input),io.presentState)
}

object NextStateGenerator extends App{
  val optionsManager = new ExecutionOptionsManager("chisel3") with HasChiselExecutionOptions with HasFirrtlOptions
  optionsManager.setTargetDirName("fpga_dir")
  Driver.execute(optionsManager,() => new NextStateGenerator)
}