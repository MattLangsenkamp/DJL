package chapt2

import ai.djl.ndarray.{NDArray, NDManager}

object Probability {
  def main(args: Array[String]): Unit = {
    val manager = NDManager.newBaseManager
    println(manager.getClass)
    val fairProbsArr = new Array[Float](6)
    for (i <- fairProbsArr.indices) {
      fairProbsArr(i) = .16666
    }
    val fairProbs = manager.create(fairProbsArr)
    println(fairProbs.sum.getFloat())
    val c = manager.create(Array(.5, .5))
    manager.randomMultinomial(8,c)
    println(manager.randomMultinomial(1, fairProbs))
  }

}
