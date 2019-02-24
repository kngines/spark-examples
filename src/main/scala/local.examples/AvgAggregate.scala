package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName AvgAggregate
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/23 17:21
  * @Version 1.0
  **/

/**
  * aggregate 柯里化方法
  * 计算过程
  *     1.初始值是(1, 0 ,0)
  *     2.number是函数中的T，即List中的元素，此时类型为Int。
  *       而acc的类型为(Int, Int, Int)。
  *       acc._1 * num是各元素相乘(初始值为1)，
  *       acc._2 + number为各元素相加。
  *     3.sum / count为计算平均数。
  */
object AvgAggregate {
  def main(args: Array[String]): Unit = {

    val sc = new SparkContext("local", "Avg Aggregate Test")
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val (mul, sum, count) = sc.parallelize(list, 2).aggregate((1, 0, 0))(
      (acc, number) => (acc._1 * number, acc._2 + number, acc._3 + 1),
      (x, y) => (x._1 * y._1, x._2 + y._2, x._3 + y._3)
    )
    println(sum / count, mul)

    println((mul, sum, count))
  }
}
