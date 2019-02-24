package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName JoinAction
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 13:44
  * @Version 1.0
  **/

/**
  * Spark内有collect方法，是Action操作中的一个算子。
  * 可以将RDD类型的数据转化为数组，同时会从远程集群是拉取数据到driver端。
  * take用于获取RDD中从0到num-1下标的元素，不排序。
  */
object JoinAction {
  def main(args: Array[String]) {

    val sc = new SparkContext("local[2]", "JoinAction Test")
    val data1 = Array[(String, Int)](("A1", 1), ("A2", 2),
      ("B1", 3), ("B2", 4),
      ("C1", 5), ("C1", 6)
    )
    val data2 = Array[(String, Int)](("A1", 7), ("A2", 8),
      ("B1", 9), ("C1", 0)
    )
    val pairs1 = sc.parallelize(data1, 3)
    val pairs2 = sc.parallelize(data2, 2)

    val result = pairs1.join(pairs2)
    println("---- start print ----")
    result.keys.map(x => x + ", ").take(200).distinct.foreach(print)
    println()
    // output
    //    A1, B1, A2, C1,
    result.collect().foreach(println)
    // output:
    // (A1,(1,7))
    // (B1,(3,9))
    // (A2,(2,8))
    //
    // (C1,(5,0))
    // (C1,(6,0))

  }

}