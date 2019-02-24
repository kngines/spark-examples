package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName Cogroup
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 21:42
  * @Version 1.0
  **/
/**
  * cogroup:对两个RDD中的KV元素，每个RDD中相同key中的元素分别聚合成一个集合。
  *   与reduceByKey不同的是针对两个RDD中相同的key的元素进行合并。
  *
  */
object Cogroup {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Cogroup Test")

    val a = sc.parallelize(List(1, 2, 1, 3), 2)
    val b = sc.parallelize(List(1, 2, 3, 4, 5, 6), 3)
    val d = a.map((_, "b"))
    d.take(20).foreach(println)
    // output:
    //    (1,b)
    //    (2,b)
    //    (1,b)
    //    (3,b)
    val e = b.map((_, "c"))
    e.take(20).foreach(println)
    // output:
    //    (1,c)
    //    (2,c)
    //    (3,c)
    //    (4,c)
    //    (5,c)
    //    (6,c)

    val result = d.cogroup(e, 4)
    result.take(20).foreach(println)
    println(result.toDebugString)
    // output:
    //    (4,(CompactBuffer(),CompactBuffer(c)))
    //    (1,(CompactBuffer(b, b),CompactBuffer(c)))
    //    (5,(CompactBuffer(),CompactBuffer(c)))
    //    (6,(CompactBuffer(),CompactBuffer(c)))
    //    (2,(CompactBuffer(b),CompactBuffer(c)))
    //    (3,(CompactBuffer(b),CompactBuffer(c)))

    //    (4) MapPartitionsRDD[5] at cogroup at Cogroup.scala:36 []
    //    |  CoGroupedRDD[4] at cogroup at Cogroup.scala:36 []
    //    +-(2) MapPartitionsRDD[2] at map at Cogroup.scala:18 []
    //    |  |  ParallelCollectionRDD[0] at parallelize at Cogroup.scala:16 []
    //    +-(3) MapPartitionsRDD[3] at map at Cogroup.scala:25 []
    //    |  ParallelCollectionRDD[1] at parallelize at Cogroup.scala:17 []
  }
}