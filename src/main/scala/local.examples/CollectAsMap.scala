package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName CollectAsMap
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/23 17:53
  * @Version 1.0
  **/

/**
  * collectAsMap 作用于K-V类型RDD上，包含重复的key。对于重复的key，后面的元素覆盖前面的元素
  */
object CollectAsMap {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "CollectAsMap Test")
    val data = Array[(String, Int)](("A", 1), ("B", 2),
      ("B", 3), ("C", 4),
      ("C", 5), ("C", 6))

    val pairs = sc.makeRDD(data, 3)

    val result = pairs.collectAsMap

    // output Map(A -> 1, C -> 6, B -> 3)
    result.foreach(println)
    print(result)
  }

}