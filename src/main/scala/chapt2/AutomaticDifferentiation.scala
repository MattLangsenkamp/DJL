package chapt2

import ai.djl.engine.Engine
import ai.djl.ndarray.types.Shape
import ai.djl.ndarray.{NDArray, NDManager}

object AutomaticDifferentiation {
  def main(args: Array[String]): Unit = {
    val manager = NDManager.newBaseManager
    val x = manager.arange(4f)
    x.setRequiresGradient(true)
    /*println(x.getGradient)


    val gc = Engine.getInstance.newGradientCollector
    try {
      val y = x.dot(x).mul(2)
      System.out.println(y)
      gc.backward(y)
    } finally if (gc != null) gc.close()

    println(x.getGradient)
    println(x.getGradient.eq(x.mul(4)))


    val gc2 = Engine.getInstance.newGradientCollector
    val y = x.sum
    gc2.backward(y)
    gc2.close()
    println(x.getGradient) */

    val gc3 = Engine.getInstance.newGradientCollector
    val y2: NDArray = x.mul(x)
    val u2 = y2.stopGradient
    val z = u2.mul(x)
    println(x)
    println(u2)
    println(z)
    gc3.backward(z)
    println(x.getGradient)
    println(x.getGradient.eq(u2))
    gc3.close()

    val gc4 = Engine.getInstance.newGradientCollector()
    val a = manager.randomNormal(new Shape(1))
    a.setRequiresGradient(true)
    val d = f(a)
    println(d)
    gc4.backward(d)
    println(a.getGradient.eq(d.div(a)))
    gc4.close()
  }

  def f(a: NDArray): NDArray = {
    var b: NDArray = a.mul(2)
    while (b.norm.getFloat() < 1000) b = b.mul(2)
    if (b.sum.getFloat() > 0) b
    else b.mul(100)
  }
}
