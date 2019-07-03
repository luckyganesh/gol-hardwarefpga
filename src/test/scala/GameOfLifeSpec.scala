import chisel3.Data
import chisel3.iotesters._
import dimensions.{Position, Size}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable

class GameOfLifeTest(c: GameOfLife, inputSeq: Seq[Seq[Boolean]], outputSeq: Seq[Seq[Boolean]], connector: MockConnector, cells: List[Cell]) extends PeekPokeTester(c) {
  val neighbourMapping = Seq(
    Seq(Position(0, 1), Position(1, 0), Position(1, 1)),
    Seq(Position(0, 0), Position(0, 2), Position(1, 0), Position(1, 1), Position(1, 2)),
    Seq(Position(0, 1), Position(1, 1), Position(1, 2)),
    Seq(Position(0, 0), Position(0, 1), Position(1, 1), Position(2, 0), Position(2, 1)),
    Seq(Position(0, 0), Position(0, 1), Position(0, 2), Position(1, 0), Position(1, 2), Position(2, 0), Position(2, 1), Position(2, 2)),
    Seq(Position(0, 1), Position(0, 2), Position(1, 1), Position(2, 1), Position(2, 2)),
    Seq(Position(1, 0), Position(1, 1), Position(2, 1)),
    Seq(Position(1, 0), Position(1, 1), Position(1, 2), Position(2, 0), Position(2, 2)),
    Seq(Position(1, 1), Position(1, 2), Position(2, 1))
  )

  cells.zipWithIndex.foreach { case (cell, i) => {
    assert(connector.connectionMapping(cell.io.initialize) == c.io.initialize)
    assert(connector.connectionMapping(cell.clock) == c.io.start)
    assert(connector.connectionMapping(cell.io.initialState) == c.io.initialState(i / inputSeq.size)(i % inputSeq.head.size))

    assert(cell.io.currentStateOfNeighbours.length == neighbourMapping(i).size)
    neighbourMapping(i).zipWithIndex.foreach { case (neighbour, index) => {
      assert(connector.connectionMapping(cell.io.currentStateOfNeighbours(index)) == cells(neighbour.x * 3 + neighbour.y).io.currentState)
    }
    }

    assert(connector.connectionMapping(cell.io.initialState) == c.io.initialState(i / inputSeq.size)(i % inputSeq.head.size))
    assert(connector.connectionMapping(c.io.currentState(i / inputSeq.size)(i % inputSeq.head.size)) == cell.io.currentState)
  }
  }
}

class MockConnector extends Connector {
  var connectionMapping = new mutable.HashMap[Data, Data]()

  override def connect(first: Data, other: Data): Unit = {
    connectionMapping.+=((first, other))
    super.connect(first, other)
  }
}

class GameOfLifeSpec extends FlatSpec with MockFactory with Matchers {
  private val connector = new MockConnector
  private var cells = List.empty[Cell]

  private def cellFactoryFunction(n: Int): Cell = {
    val cell = new Cell(n)
    cells = cells.:+(cell)
    cell
  }

  it should "form the cell matrix and connect appropriate neighbours" in {
    Driver(() => new GameOfLife(Size(3, 3), cellFactoryFunction, connector)) {
      c => {
        val inputSeq = Seq(Seq(false, false, false), Seq(true, true, true), Seq(false, false, false))
        val outputSeq = Seq(Seq(false, true, false), Seq(false, true, false), Seq(false, true, false))
        new GameOfLifeTest(c, inputSeq, outputSeq, connector, cells)
      }
    } should be(true)
  }
}