package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName Cartesian
  * @Description TODO Cartesian 笛卡尔
  * @Author kngin
  * @Date: 2019/2/19 22:06
  * @Version 1.0
  **/

/**
  * Spark 创建RDD的方式：1. 从集合中创建RDD 2. 从外部存储中创建RDD 3. 从其他RDD创建
  *   从集合中创建RDD， Spark 主要提供了 2 种函数：parallelize和makeRDD
  *
  */
object Cartesian {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "Cartesian Test")

    val x = sc.parallelize(List(1, 2, 3, 4, 5))  // 从集合中创建RDD
    val y = sc.parallelize(List(6, 7, 8, 9, 10))

    println("---- init x,y ----")
    println(x ++ y ++ x)
    println("---- init x,y ----")

    val result = x.cartesian(y)  // 参数是RDD，求两个RDD的笛卡儿积
    result.foreach(println)

  }
}
