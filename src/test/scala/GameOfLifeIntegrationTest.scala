import chisel3.iotesters._
import dimensions.Size

class GameOfLifeIntegrationTest(c: GameOfLife) extends PeekPokeTester(c) {
  poke(c.io.enable,0)

  val inputSeq = Seq(Seq(false,false,false),Seq(true,true,true),Seq(false,false,false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      poke(c.io.initialState(i)(j), inputSeq(i)(j))
    }
  }
  step(4)
  poke(c.io.enable,1)

  step(1)
  val expectation1 = Seq(Seq(false,true,false),Seq(false,true,false),Seq(false,true,false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation1(i)(j))
    }
  }

  step(1)
  val expectation2 = Seq(Seq(false,false,false),Seq(true,true,true),Seq(false,false,false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation2(i)(j))
    }
  }

  step(1)
  val expectation3 = Seq(Seq(false,true,false),Seq(false,true,false),Seq(false,true,false))

  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation3(i)(j))
    }
  }

}

object GameOfLifeIntegrationTest extends App {
  Driver(() => new GameOfLife(Size(3,3), n => new Cell(n), new Connector)){
    c => {
      new GameOfLifeIntegrationTest(c)
    }
  }
}