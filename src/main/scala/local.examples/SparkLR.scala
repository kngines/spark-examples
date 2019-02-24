package local.examples

import java.util.Random
import scala.math.exp
import breeze.linalg.{DenseVector, Vector}
import org.apache.spark._

/**
  * @ObjectName SparkLR
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 16:51
  * @Version 1.0
  **/

/**
  * MLlib提供了两种类型的本地向量：
  * 稠密向量 DenseVector
  * 稀疏向量 SparseVector
  *
  */
object SparkLR {
  val N = 10000 // 数据点个数
  val D = 10 // 维度
  val R = 0.7 // 比例系数
  val ITERATIONS = 5  // 设置迭代次数
  val rand = new Random(42)

  case class DataPoint(x: Vector[Double], y: Double)

  def generateData = {
    def generatePoint(i: Int) = {
      val y = if (i % 2 == 0) -1 else 1
      val x = DenseVector.fill(D) {
        rand.nextGaussian + y * R
      }
//      println(x.toString() + " " + y)
      DataPoint(x, y)

    }

    Array.tabulate(N)(generatePoint) // 返回指定长度数组，每个数组元素为指定函数的返回值，默认从 0 开始。
    // 例子输出:
    //    scala> Array.tabulate(3)(a => a + 5)
    //    res0: Array[Int] = Array(5, 6, 7)
  }

  def main(args: Array[String]) {
//    val sparkConf = new SparkConf().setAppName("SparkLR")
//    val sc = new SparkContext(sparkConf)

    val sc = new SparkContext("local" , "SparkLR")

    val numSlices = if (args.length > 0) args(0).toInt else 2
    val points = sc.parallelize(generateData, numSlices).cache()

    // Initialize w to a random value
    var w = DenseVector.fill(D) {
      2 * rand.nextDouble - 1
    }
    println("Initial w: " + w)

    for (i <- 1 to ITERATIONS) {
      println("On iteration " + i)
      val gradient = points.map { p =>
        p.x * (1 / (1 + exp(-p.y * (w.dot(p.x)))) - 1) * p.y
      }.reduce(_ + _)
      w -= gradient
    }

    println("Final w: " + w)
    sc.stop()
  }
}