import chisel3.iotesters.{Driver, PeekPokeTester}

class NextStateGeneratorTest(c: NextStateGenerator) extends PeekPokeTester(c) {
  val inputSeq = Seq(1,1,1,0,0,0,0,0)

  for (i <- inputSeq.indices){
    poke(c.io.input(i), inputSeq(i))
  }
  poke(c.io.presentState,1)
  expect(c.io.nextState,1)
}

object NextStateGeneratorTest extends App {
  Driver(() => new NextStateGenerator){
    c => new NextStateGeneratorTest(c)
  }
}