package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName reduceActionTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 16:32
  * @Version 1.0
  **/

/**
  * fold: 通过op函数聚合各分区中的元素及合并各分区的元素
  */
object reduceActionTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "MapPartitionsRDD Test")
    val data1 = Array[(String, Int)](("K1", 1), ("K2", 2),
      ("U1", 3), ("U2", 4),
      ("W1", 3), ("W2", 4)
    )
    val pairs = sc.parallelize(data1, 3)

    val result = pairs.fold(("K0", 10))((A, B) => (A._1 + "#" + B._1, A._2 + B._2))
    //    val foldRDD = rdd.fold(("d", 0))((val1, val2) => { if (val1._2 >= val2._2) val1 else val2})
    println(result)
  }

}