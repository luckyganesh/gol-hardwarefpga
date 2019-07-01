import chisel3._
import chisel3.iotesters.{Driver, PeekPokeTester}

class NextStateGeneratorTest(c: TestModule) extends PeekPokeTester(c) {
  val inputSeq = Seq(1,1,1,0,0,0,0,0)
  for (i <- inputSeq.indices){
    poke(c.io.input(i), inputSeq(i))
  }

  poke(c.io.presentState, 1)
  step(1)
  expect(c.io.nextState, 1)
}

class TestModule extends Module {
  val io = IO(new Bundle{
    val input = Input(Vec(8, Bool()))
    val presentState = Input(Bool())
    val nextState = Output(Bool())
  })
  io.nextState := new NextStateGenerator().getNextState(io.input, io.presentState)
}

object NextStateGeneratorTest extends App {
  Driver(() => new TestModule){
    c => new NextStateGeneratorTest(c)
  }
}