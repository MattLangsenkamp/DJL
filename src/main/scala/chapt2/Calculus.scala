package chapt2

import tech.tablesaw.plotly.components.{Axis, Figure, Layout}
import tech.tablesaw.plotly.traces.ScatterTrace
import ai.djl.ndarray.NDManager
import ai.djl.ndarray.types.DataType
import tech.tablesaw.plotly.Plot

object Calculus {

  def main(args: Array[String]): Unit = {
    val l = (x: Double) => 3 * Math.pow(x, 2) - 4 * x

    def numericalLim(f: Double => Double, x: Double, h: Double) = (f.apply(x + h) - f.apply(x)) / h

    List(1, 2, 3, 4, 5).foldLeft(0.1)(
      (h: Double, i: Int) =>
        println("h=" + String.format("%.5f", h) + ", numerical limit="
          + String.format("%.5f", numericalLim(l, 1, h)))
        h * 0.1
    )

    val manager = NDManager.newBaseManager
    val X = manager.arange(0f, 3f, 0.1f, DataType.FLOAT64)

    val x = X.toDoubleArray

    val fx = new Array[Double](x.length)
    for (i <- 0 until x.length) {
      fx(i) = l(x(i))
    }

    val fg = new Array[Double](x.length)
    for (i <- 0 until x.length) {
      fg(i) = 2 * x(i) - 3
    }
    val p = plotLineAndSegment(x, fx, fg, "f(x)", "Tangent line(x=1)", "x", "f(x)", 700, 500)
    Plot.show(p)
  }


  def plotLineAndSegment(x: Array[Double], y: Array[Double], segment: Array[Double],
                         trace1Name: String, trace2Name: String,
                         xLabel: String, yLabel: String, width: Int, height: Int): Figure =
    val trace = ScatterTrace
      .builder(x, y)
      .mode(ScatterTrace.Mode.LINE)
      .name(trace1Name).build

    val trace2 = ScatterTrace
      .builder(x, segment)
      .mode(ScatterTrace.Mode.LINE)
      .name(trace2Name).build

    val layout = Layout
      .builder
      .height(height)
      .width(width)
      .showLegend(true)
      .xAxis(Axis.builder.title(xLabel).build)
      .yAxis(Axis.builder.title(yLabel).build).build

    Figure(layout, trace, trace2)
}
