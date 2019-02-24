package local.examples

import org.apache.spark.{HashPartitioner, RangePartitioner, SparkContext}

/**
  * @ObjectName partitionByTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 15:13
  * @Version 1.0
  **/

/**
  * partitionBy：
  *   根据partitioner函数生成新的ShuffleRDD，将原RDD重新分区
  *
  *   注意重新分区后， 输出结果的顺序
  */
object partitionByTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "ReduceByKeyToDriver Test")
    val data1 = Array[(String, Int)](("K", 1), ("T", 2),
      ("T", 3), ("W", 4),
      ("W", 5), ("W", 6)
    )
    val pairs = sc.parallelize(data1, 3)

    val result = pairs.partitionBy(new RangePartitioner(2, pairs, true))  // 使用partitionBy重分区
    println("---- partitionBy result (RangePartitioner)----")
    result.take(200).foreach(println)
    //    (K,1)
    //    (T,2)
    //    (T,3)
    //    (W,4)
    //    (W,5)
    //    (W,6)

    val result2 = pairs.partitionBy(new HashPartitioner(2))  // 使用partitionBy重分区
    println("---- partitionBy result (HashPartitioner)----")
    result2.take(200).foreach(println)
    //    (T,2)
    //    (T,3)
    //    (K,1)
    //    (W,4)
    //    (W,5)
    //    (W,6)
  }
}