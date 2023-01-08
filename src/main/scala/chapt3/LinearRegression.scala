package chapt3
import ai.djl.*
import ai.djl.ndarray.NDManager
import ai.djl.ndarray.index.NDIndex
import ai.djl.ndarray.types.{DataType, Shape}
import tech.tablesaw.api.*
import tech.tablesaw.plotly.api.*
import tech.tablesaw.plotly.components.*
import tech.tablesaw.api.FloatColumn
import tech.tablesaw.plotly.Plot

import java.util.stream.*


object LinearRegression {

  def main(args: Array[String]): Unit = {
    val n = 10000
    val manager = NDManager.newBaseManager
    val a = manager.ones(new Shape(n))
    val b = manager.ones(new Shape(n))

    val c = manager.zeros(new Shape(n))
    time {
      var i = 0
      while (i < n) {
        c.set(new NDIndex(i), a.getFloat(i) + b.getFloat(i))
        i += 1
      }
    } ("loops")

    time {
      a.add(b)
    } ("vectorization")

    val start = -7
    val end = 14
    val step = 0.01f
    val count = (end / step).toInt

    val x = new Array[Float](count)

    for (i <- 0 until count) {
      x(i) = start + i * step
    }


    val y1 = normal(x, 0, 1)
    val y2 = normal(x, 0, 2)
    val y3 = normal(x, 3, 1)


    val params = Array.fill(x.length)("mean 0, var 1")
      .appendedAll(Array.fill(x.length)("mean 0, var 2"))
      .appendedAll(Array.fill(x.length)("mean mean, var 1"))

    val z = combine3(x, x, x).foldLeft(FloatColumn.create("z"))((c, f) => c.append(f))
    val p = combine3(y1, y2, y3).foldLeft(FloatColumn.create("p(z)"))((c, f) => c.append(f))
    val prms = params.foldLeft(StringColumn.create("params"))((c, f) => c.append(f))
    val normalDistributions = Table.create("normal")
      .addColumns(
        z, p, prms
      )
    val fig = LinePlot.create("Normal Distributions", normalDistributions, "z", "p(z)", "params")
    Plot.show(fig)
  }

  def combine3(x: Array[Float], y: Array[Float], z: Array[Float]): Array[Float] = x.appendedAll(y).appendedAll(z)

  def normal(z: Array[Float], mu: Float, sigma: Float): Array[Float] = {
    val dist = Array.fill(z.length)(0.0f)
    for (i <- z.indices) {
      val p = 1.0f / Math.sqrt(2 * Math.PI * sigma * sigma).toFloat
      dist(i) = p * Math.pow(Math.E, -0.5 / (sigma * sigma) * (z(i) - mu) * (z(i) - mu)).toFloat
    }
    dist
  }

  private def time[R](block: => R)(message: String): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    val t = (t1 - t0) / 1e9
    if (t > 0.0001) println(s"Elapsed time to complete $message: " + t + "s")
    result
  }
}
