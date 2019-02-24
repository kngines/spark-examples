package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName TakeActionTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 17:23
  * @Version 1.0
  **/

/**
  * RDD有两种操作算子：
  *   Transformation（转换）：Transformation属于延迟计算，当一个RDD转换成另一个RDD时并没有立即进行转换，仅是记住数据集的逻辑操作。
  *   Ation（执行）：触发Spark作业的运行，真正触发转换算子的计算
  */
object TakeActionTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "TakeAction Test")
    val data1 = Array[(String, Int)](("K1", 1), ("K2", 2),
      ("U1", 3), ("U2", 4),
      ("W1", 3), ("W2", 4)
    )
    val pairs = sc.parallelize(data1, 3)
    val result = pairs.take(5)
    result.foreach(println)
  }
}
