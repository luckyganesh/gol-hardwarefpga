import chisel3.iotesters._
import dimensions.Size
import org.scalatest.{FlatSpec, Matchers}

class GameOfLifeIntegrationTest1(c: GameOfLife) extends PeekPokeTester(c) {
  poke(c.io.start, 0)
  step(4)
  poke(c.io.rows, 3)
  poke(c.io.columns, 3)
  poke(c.io.start, 1)
  val inputSeq: Seq[Seq[Boolean]] = Seq(Seq(false, false, false), Seq(true, true, true), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      poke(c.io.initialState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  val expectation1: Seq[Seq[Boolean]] = Seq(Seq(false, true, false), Seq(false, true, false), Seq(false, true, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation1(i)(j))
    }
  }

  step(1)
  val expectation2: Seq[Seq[Boolean]] = Seq(Seq(false, false, false), Seq(true, true, true), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation2(i)(j))
    }
  }

  step(1)
  val expectation3: Seq[Seq[Boolean]] = Seq(Seq(false, true, false), Seq(false, true, false), Seq(false, true, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation3(i)(j))
    }
  }

}

class GameOfLifeIntegrationTest2(c: GameOfLife) extends PeekPokeTester(c) {
  poke(c.io.start, 0)
  step(4)

  poke(c.io.rows, 2)
  poke(c.io.columns, 2)
  poke(c.io.start,1)
  val inputSeq: Seq[Seq[Boolean]] = Seq(Seq(false, true, false), Seq(true, true, true), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      poke(c.io.initialState(i)(j), inputSeq(i)(j))
    }
  }

  step(1)
  val initializationExpectation: Seq[Seq[Boolean]] = Seq(Seq(false, true, false), Seq(true, true, false), Seq(false, false, false))
  for (i <- initializationExpectation.indices) {
    for (j <- initializationExpectation(i).indices) {
      expect(c.io.currentState(i)(j),initializationExpectation(i)(j))
    }
  }

  step(1)
  val expectation1: Seq[Seq[Boolean]] = Seq(Seq(true,true,false),Seq(true,true,false), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation1(i)(j))
    }
  }

  step(1)
  val expectation2: Seq[Seq[Boolean]] = Seq(Seq(true,true,false),Seq(true,true,false), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation2(i)(j))
    }
  }

  step(1)
  val expectation3: Seq[Seq[Boolean]] = Seq(Seq(true,true,false),Seq(true,true,false), Seq(false, false, false))
  for (i <- inputSeq.indices) {
    for (j <- inputSeq(i).indices) {
      expect(c.io.currentState(i)(j), expectation3(i)(j))
    }
  }
}

class GameOfLifeIntegrationSpec extends FlatSpec with Matchers {
  it should "simulate game of life for total rows and total columns" in {
    Driver(() => new GameOfLife(Size(3, 3), n => new Cell(n), new Connector)) {
      c => {
        new GameOfLifeIntegrationTest1(c)
      }
    } should be(true)
    //    System.exit(0)
  }

  it should "simulate game of life with less no of rows and columns" in {
    Driver(() => new GameOfLife(Size(3, 3), n => new Cell(n), new Connector)) {
      c => {
        new GameOfLifeIntegrationTest2(c)
      }
    } should be(true)
  }
}