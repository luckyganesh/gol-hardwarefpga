import chisel3._

class NextStateGeneratorBundle extends Bundle {
  val input = Input(Vec(8,Bool()))
  val presentState = Input(Bool())
  val nextState = Output(Bool())
}