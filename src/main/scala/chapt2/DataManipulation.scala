package chapt2
import ai.djl.*
import ai.djl.ndarray.NDManager
import ai.djl.ndarray.types.{DataType, Shape}

object DataManipulation {

  def main(array: Array[String]): Unit = {
    val manager = NDManager.newBaseManager();
    val x = manager.arange(12)
    print(x)
    print(x.size())
    println(x.reshape(3,4))
    println(manager.zeros(new Shape(2,3,4)))
    println(manager.randomNormal(0f, 1f, new Shape(3, 4), DataType.FLOAT32))
    println(manager.getDevice)
    println(manager.create(Array[Float](2, 1, 4, 3, 1, 2, 3, 4, 4, 3, 2, 1), new Shape(3, 4)))

    val x2 = manager.create(Array[Float](1f, 2f, 4f, 8f))
    val y = manager.create(Array[Float](2f, 2f, 2f, 2f))
    println(x2.add(y))

    val x3 = manager.arange(12f).reshape(3, 4)
    val y2 = manager.create(Array[Float](2, 1, 4, 3, 1, 2, 3, 4, 4, 3, 2, 1), new Shape(3, 4))
    println(x3.concat(y2))

    val a = manager.arange(3f).reshape(3, 1)
    val b = manager.arange(2f).reshape(1, 2)
    println(a.add(b))
  }
}
