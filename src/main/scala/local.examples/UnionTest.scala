package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName UnionTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 17:24
  * @Version 1.0
  **/

object UnionTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "ReduceByKeyToDriver Test")

    val data1 = Array[(String, Int)](("K1", 1), ("K2", 2),
      ("U1", 3), ("U2", 4),
      ("W1", 5), ("W1", 6)
    )

    val data2 = Array[(String, Int)](("K1", 7), ("K2", 8),
      ("U1", 9), ("W1", 0)
    )
    val pairs1 = sc.parallelize(data1, 3)
    val pairs2 = sc.parallelize(data2, 2)
    val result = pairs1.union(pairs2)
    result.take(200).foreach(println)
  }
}