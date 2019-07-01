import chisel3.iotesters._

class GameOfLifeTest(c: GameOfLife, inputSeq: Seq[Seq[Boolean]], outputSeq: Seq[Seq[Boolean]]) extends PeekPokeTester(c) {
  poke(c.io.enable,0)

  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      poke(c.io.currentState(i)(j), inputSeq(i)(j))
    }
  }
  step(4)
  poke(c.io.enable,1)

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), outputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), outputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), outputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.nextState(i)(j), outputSeq(i)(j))
    }
  }
}

object GameOfLifeTest extends App {
  Driver(() => new GameOfLife(3,3, (n) => new Cell(n))){
    c => {
      val inputSeq = Seq(Seq(false,false,false),Seq(true,true,true),Seq(false,false,false))
      val outputSeq = Seq(Seq(false,true,false),Seq(false,true,false),Seq(false,true,false))
      new GameOfLifeTest(c,inputSeq,outputSeq)
    }
  }
}