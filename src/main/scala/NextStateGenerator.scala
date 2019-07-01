import chisel3._

class NextStateGenerator extends Module {
  val io = IO(new Bundle{
    val input = Input(Vec(8,Bool()))
    val presentState = Input(Bool())
    val nextState = Output(Bool())
  })

  private def calculate(vec: Vec[Bool]): UInt = vec.count(x => x)

  private def getNextState(numberOfNeighboursAlive:UInt,presentState:Bool): Bool = {
    Mux(numberOfNeighboursAlive === 2.U, presentState,numberOfNeighboursAlive === 3.U)
  }

  io.nextState := getNextState(calculate(io.input),io.presentState)
}

object NextStateGenerator extends App{
  Driver.execute(args,() => new NextStateGenerator)
}