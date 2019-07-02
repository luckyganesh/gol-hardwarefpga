import chisel3.Data

class Connector {
  def connect(first: Data, other:Data) = first := other
}