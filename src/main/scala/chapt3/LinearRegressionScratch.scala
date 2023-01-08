package chapt3

import ai.djl.ndarray.index.NDIndex
import ai.djl.ndarray.types.{DataType, Shape}
import ai.djl.ndarray.{NDArray, NDManager}
import com.ibm.icu.impl.LocaleDisplayNamesImpl.DataTable
import tech.tablesaw.api.{FloatColumn, Table}
import tech.tablesaw.plotly.Plot
import tech.tablesaw.plotly.api.ScatterPlot

object LinearRegressionScratch {

  def main(args: Array[String]): Unit = {
    val manager = NDManager.newBaseManager
    val trueW = manager.create(Array[Float](2, -3.4f))
    val trueB = 4.2f
    val dp = syntheticData(manager, trueW, trueB, 1000)
    val features = dp.X
    val labels = dp.y

    println(f"features: [${features.get(0).getFloat(0)}, ${features.get(0).getFloat(1)}]")
    println("label: " + labels.getFloat(0))

    val X = features.get(new NDIndex(":, 1")).toFloatArray
    val y = labels.toFloatArray
    val columnX = X.foldLeft(FloatColumn.create("X"))((c, f) => c.append(f))
    val columnY = y.foldLeft(FloatColumn.create("y"))((c, f) => c.append(f))
    val data = Table.create("Data").addColumns(columnX, columnY)

    val fig = ScatterPlot.create("Synthetic Data", data, "X", "y")
    Plot.show(fig)
  }

  private case class DataPoints(X: NDArray, y: NDArray)


  private def syntheticData(manager: NDManager, w: NDArray, b: Float, numExamples: Int): DataPoints =
    val X = manager.randomNormal(new Shape(numExamples, w.size))
    var y = X.dot(w).add(b)
    // Add noise
    y = y.add(manager.randomNormal(0, 0.01f, y.getShape, DataType.FLOAT32))
    DataPoints(X, y)
}
