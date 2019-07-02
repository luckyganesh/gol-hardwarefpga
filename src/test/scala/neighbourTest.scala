import org.scalatest.FunSuite
import dimensions._
import neighbourFinder._

class neighbourTest extends FunSuite {

  test("testGetNeighbours when there are no neighbours") {
    assert(getNeighbours(Position(0,0),Size(1,1)) == List.empty)
  }

  test("testGetNeighbours when there are one neighbour") {
    assert(getNeighbours(Position(0,0),Size(1,2)) == List(Position(0,1)))
  }

  //two neighbours are not possible

  test("testGetNeighbours when there are three neighbour") {
    assert(getNeighbours(Position(0,0),Size(2,2)) == List(Position(0,1),Position(1,0),Position(1,1)))
  }

  //four neighbours are not possible

  test("testGetNeighbours when there are five neighbours") {
    assert(getNeighbours(Position(0,1),Size(3,3)) == List(Position(0,0),Position(0,2),Position(1,0),Position(1,1),Position(1,2)))
  }

  //six and seven neighbours are not possible

  test("testGetNeighbours when there are eight neighbours") {
    assert(getNeighbours(Position(1,1),Size(3,3)) == List(Position(0,0),Position(0,1),Position(0,2),Position(1,0),Position(1,2),Position(2,0),Position(2,1),Position(2,2)))
  }

}
