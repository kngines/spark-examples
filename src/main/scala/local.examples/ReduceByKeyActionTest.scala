package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName ReduceByKeyActionTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 16:43
  * @Version 1.0
  **/
object ReduceByKeyActionTest {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "ReduceByKeyToDriver Test")
    val data1 = Array[(String, Int)](("K", 1), ("U", 2),
      ("U", 3), ("W", 4),
      ("W", 5), ("W", 6))
    val pairs = sc.parallelize(data1, 3)

    val result = pairs.reduceByKey(_ + _, 2)  // reduceByKey(func, [numTasks])
    result.take(200).foreach(println)
  }

}