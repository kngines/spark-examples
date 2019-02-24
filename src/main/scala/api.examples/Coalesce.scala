package api.examples

import org.apache.spark.SparkContext
import scala.collection.mutable.Map

/**
  * @ObjectName Coalesce
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 21:17
  * @Version 1.0
  **/
object Coalesce {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Coalesce Test")

    val y = sc.parallelize(1 to 10, 10)

    y.take(200).foreach(println)
    println("y 分区个数: " + y.getNumPartitions)
    val z = y.coalesce(2, true)
    // outout
    //    1
    //    2
    //    3
    //    4
    //    5
    //    6
    //    7
    //    8
    //    9
    //    10
    //    y 分区个数: 10

    z.take(200).foreach(println)
    println("z 分区个数: " + z.getNumPartitions)
    // output
    //    1
    //    3
    //    4
    //    7
    //    9
    //    2
    //    5
    //    6
    //    8
    //    10
    //    z 分区个数: 2


    // 打印分区明细数据，可以尝试其他方法
    val r = z.mapPartitionsWithIndex {
      (partId, iter) => {
        val part_map = Map[String, List[Int]]()
        val part_name = "part_" + partId
        part_map(part_name) = List[Int]()
        while (iter.hasNext) {
          part_map(part_name) :+= iter.next() // :+= 列表尾部追加元素
        }
        part_map.iterator
      }
    }.collect
    println("分区明细数据: ")
    r.foreach(println)

    // output
    //    分区明细数据:
    //    (part_0,List(1, 3, 4, 7, 9))
    //    (part_1,List(2, 5, 6, 8, 10))
  }
}