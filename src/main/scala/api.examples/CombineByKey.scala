package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName CombineByKey
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 22:03
  * @Version 1.0
  **/

/**
  * combineByKey:
  *   combineByKey三个参数
  *     第一个参数只会处理key第一次出现的元素
  *     第二个参数处理出现过的key的元素（将新加入的key和之前处理过的key进行处理）
  *     第三个参数是将每个分区的结果进行汇总然后分组再处理
  *
  * :: 该方法被称为cons，意为构造，向队列的头部追加数据，创造新的列表
  *
  * :+和+: 两者的区别在于:+方法用于在尾部追加元素，+:方法用于在头部追加元素
  *
  * ++ 该方法用于连接两个集合
  *
  * ::: 该方法只能用于连接两个List类型的集合
  *
  */
object CombineByKey {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "CombineByKey Test")

    val a = sc.parallelize(List("dog", "cat", "gnu", "salmon", "rabbit", "turkey", "wolf", "bear", "bee"), 3)
    val b = sc.parallelize(List(1, 1, 2, 2, 2, 1, 2, 2, 2), 3)
    val c = b.zip(a)
    c.take(20).foreach(println)
    //    (1,dog)
    //    (1,cat)
    //    (2,gnu)
    //    (2,salmon)
    //    (2,rabbit)
    //    (1,turkey)
    //    (2,wolf)
    //    (2,bear)
    //    (2,bee)
    val d = c.combineByKey(List(_), (x: List[String], y: String)
    => y :: x, (x: List[String], y: List[String]) => x ::: y)

    val result = d.collect
    result.foreach(println)
    println("RDD graph:\n" + d.toDebugString)

    // output
    //    (1,List(cat, dog, turkey))
    //    (2,List(gnu, rabbit, salmon, bee, bear, wolf))
    //    RDD graph:
    //      (3) ShuffledRDD[3] at combineByKey at CombineByKey.scala:21 []
    //    +-(3) ZippedPartitionsRDD2[2] at zip at CombineByKey.scala:19 []
    //    |  ParallelCollectionRDD[1] at parallelize at CombineByKey.scala:18 []
    //    |  ParallelCollectionRDD[0] at parallelize at CombineByKey.scala:17 []
  }
}