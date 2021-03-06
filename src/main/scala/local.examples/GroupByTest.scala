package local.examples

import java.util.Random
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @ObjectName GroupByTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 12:43
  * @Version 1.0
  **/

object GroupByTest {
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("GroupBy Test").setMaster("local[2]")
    var numMappers = 10
    var numKVPairs = 100
    var valSize = 100
    var numReducers = 3

    val sc = new SparkContext(sparkConf)

    val pairs1 = sc.parallelize(0 until numMappers, numMappers).flatMap { p =>
      val ranGen = new Random
      var arr1 = new Array[(Int, Array[Byte])](numKVPairs)
      for (i <- 0 until numKVPairs) {
        val byteArr = new Array[Byte](valSize)
        ranGen.nextBytes(byteArr)
        arr1(i) = (ranGen.nextInt(10), byteArr)
      }
      arr1
    }.cache
    // Enforce that everything has been calculated and in cache
    pairs1.count

    val result = pairs1.groupByKey(numReducers)
    result.foreach(println)
    println(result.count)
    println(result.toDebugString)

    sc.stop()
  }
}