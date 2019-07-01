import chisel3.iotesters._

class GameOfLifeTest(c: GameOfLife, inputSeq: Seq[Seq[Boolean]], outputSeq: Seq[Seq[Boolean]]) extends PeekPokeTester(c) {
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      poke(c.io.currentState(i)(j), inputSeq(i)(j))
    }
  }

  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), outputSeq(i)(j))
    }
  }
}

object GameOfLifeTest extends App {
  Driver(() => new GameOfLife(3,3, new NextStateGenerator)){
    c => {
      val inputSeq = Seq(Seq(false,false,false),Seq(true,true,true),Seq(false,false,false))
      val outputSeq = Seq(Seq(false,true,false),Seq(false,true,false),Seq(false,true,false))
      new GameOfLifeTest(c,inputSeq,outputSeq)
    }
  }
}