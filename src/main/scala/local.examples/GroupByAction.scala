package local.examples

import org.apache.spark.{RangePartitioner, SparkContext}

/**
  * @ObjectName GroupByAction
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 12:22
  * @Version 1.0
  **/

/**
  * Spark RDD的宽依赖中存在Shuffle过程，Spark的Shuffle过程同MapReduce，也依赖于Partitioner数据分区器。
  *   HashPartitioner：根据RDD中key的hashcode值进行分区
  *   RangePartitioner：根据范围进行数据分区。 主要用于RDD的数据排序相关API中
  */
object GroupByAction {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "GroupByAction Test")

    val data = Array[(String, Int)](("A1", 1), ("A2", 2),
      ("B1", 6), ("A2", 4),
      ("B1", 3), ("B1", 5))

    val pairs = sc.parallelize(data, 3)
    pairs.foreach(println)

    val result1 = pairs.groupBy(K => K._1)
    val result2 = pairs.groupBy((K: (String, Int)) => K._1, 1)

    // new RangePartitioner(partitions: Int, rdd: RDD[_ <: Product2[K, V]], ascending: Boolean)
    val result3 = pairs.groupBy((K: (String, Int)) => K._1, new RangePartitioner(3, pairs))

    println("---- result1 ----")
    result1.foreach(println)
    //    (A1,CompactBuffer((A1,1)))
    //    (B1,CompactBuffer((B1,6), (B1,3), (B1,5)))
    //    (A2,CompactBuffer((A2,2), (A2,4)))

    println("---- result2 ----")
    result2.foreach(println)
    //    (A1,CompactBuffer((A1,1)))
    //    (B1,CompactBuffer((B1,6), (B1,3), (B1,5)))
    //    (A2,CompactBuffer((A2,2), (A2,4)))

    println("---- result3 ----")
    result3.foreach(println)
    //    (A1,CompactBuffer((A1,1)))
    //    (A2,CompactBuffer((A2,2), (A2,4)))
    //    (B1,CompactBuffer((B1,6), (B1,3), (B1,5)))

  }

}