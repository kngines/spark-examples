package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName GroupByKeyPair
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 22:28
  * @Version 1.0
  **/
object GroupByKeyPair {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "GroupByKeyPair Test")
    val d = sc.parallelize(1 to 100, 10)
    val pairs = d.keyBy(x => x % 10)
    pairs.take(200).foreach(println)
    val result1 = pairs.groupByKey()

    println("Result 1:")
    result1.take(2).foreach(println)

    //    (0,CompactBuffer(10, 20, 30, 40, 50, 60, 70, 80, 90, 100))
    //    (1,CompactBuffer(1, 11, 21, 31, 41, 51, 61, 71, 81, 91))
  }
}