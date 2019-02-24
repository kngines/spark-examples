package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName Aggregate
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/23 17:13
  * @Version 1.0
  **/

/**
  * 传入三个参数：
  *   聚合的初始值：zeroValue: U
  *   对序列操作的函数：seqOp
  *   聚合函数：combOp
  * aggregate函数将每个分区进行seqOp，且从zeroValue开始遍历分区里的所有元素。
  *   然后用combOp。从zeroValue开始遍历所有分区的结果。
  */
object Aggregate {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "AggregateAction Test")
    val data = Array[(String, Int)](("A1", 1), ("A2", 2),
      ("B1", 3), ("B2", 4),
      ("C1", 5), ("C2", 6))

    val pairs = sc.parallelize(data, 3)

    // output:
    // 	(A1,1)(A2,2)
    //  (B1,3)(B2,4)
    //	(C1,5)(C2,6)
    pairs.foreach(println)

    val result = pairs.aggregate(("", 0))((U, T) => (U._1 + T._1, U._2 + T._2), (U, T) =>
      ("[" + U._1 + T._1 + "] ", U._2 + T._2))

    // output ([[[A1A2] B1B2] C1C2] ,21)
    println(result)
  }
}