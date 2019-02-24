package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName Collect
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 21:53
  * @Version 1.0
  **/

/**
  * Spark内有collect方法，是Action操作中的一个算子，可以将RDD类型的数据转化为数组，同时会从远程集群是拉取数据到driver端。
  */
object Collect {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Collect Test")

    val c = sc.parallelize(List("Gnu", "cat", "Rat", "Dog", "Gnu", "Rat"), 2)

    val result = c.collect // Action操作, 返回RDD所有元素
    result.foreach(println)
  }
}