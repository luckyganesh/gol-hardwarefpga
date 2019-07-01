import chisel3._

class NextStateGenerator {
  private def numberOfAliveNeighbours(vec: Vec[Bool]): UInt = vec.count(n => n)

  def getNextState(neighbours: Vec[Bool], presentState:Bool): Bool = {
    val numberOfNeighboursAlive = numberOfAliveNeighbours(neighbours)
    printf("%d ****", numberOfNeighboursAlive)
    Mux(numberOfNeighboursAlive === 2.U, presentState,numberOfNeighboursAlive === 3.U)
  }

}