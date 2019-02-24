package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName CollectAsMap
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 21:56
  * @Version 1.0
  **/

/**
  * collectAsMap:
  * 作用于K-V类型的RDD上，作用与collect不同的是collectAsMap函数不包含重复的key，对于重复的key。后面的元素覆盖前面的元素。
  *
  * zip拉链操作:
  * 将两个RDD组合成Key/Value形式的RDD,默认两个RDD的partition数量以及元素数量都相同，否则会抛出异常。
  * 都是第i个值和第i个值进行连接。
  * zipPartitions:
  * 将多个RDD按照partition组合成为新的RDD，该函数需要组合的RDD具有相同的分区数，但对于每个分区内的元素数量没有要求。
  */
object CollectAsMap {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "CollectAsMap Test")

    val a = sc.parallelize(List(1, 2, 1, 3), 1)
    val b = a.zip(a)
    b.take(20).foreach(println)
    // output
    //    (1,1)
    //    (2,2)
    //    (1,1)
    //    (3,3)
    val result = b.collectAsMap
    result.foreach(println)

    // output:
    // (2,2)
    // (1,1)
    // (3,3)
  }
}