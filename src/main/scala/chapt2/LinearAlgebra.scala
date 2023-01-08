package chapt2
import ai.djl.*
import ai.djl.ndarray.NDManager
import ai.djl.ndarray.index.NDIndex
import ai.djl.ndarray.types.{DataType, Shape}

object LinearAlgebra {

  def main(args: Array[String]) : Unit = {
    val manager = NDManager.newBaseManager
    val x = manager.create(3f)
    val y = manager.create(2f)
    println(x.add(y))
    println(x mul y)
    println(x div y)
    println(x pow y)
    val vec = manager.arange(4f)
    println(vec)
    println(vec get 3)
    println(vec.getShape)
    val A = manager.arange(20f).reshape(5, 4)
    println(A)
    println(A.get(new NDIndex(0,3)))
    println(A.transpose)
    val X = manager.arange(24f).reshape(2, 3, 4)
    println(X)
    val AA = manager.arange(20f).reshape(5, 4)
    val BB = AA.duplicate
    println(BB)
    println(AA mul BB)
    val B = manager.ones(new Shape(4, 3))
    println(AA dot B)
  }
}
