package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName LookUpTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/23 16:52
  * @Version 1.0
  **/

/**
  * lookup函数对<key,value>型的rdd操作，返回指定key对应的元素形成的seq。
  * 如果rdd包含分区器，那么只扫描对应key所在分区，然后返回对应key的元素形成的seq；
  * 如果rdd没有分区器，则对rdd进行全盘扫描，然后返回对应key的元素形成的seq
  */
object LookUpTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "LookUp Test")

    val data = Array[(String, Int)](("A", 1), ("B", 2),
      ("B", 3), ("C", 4),
      ("C", 5), ("C", 6))

    val pairs = sc.parallelize(data, 3)

    val finalRDD = pairs.lookup("B")

    finalRDD.foreach(println)
    // output:
    // 2
    // 3
  }
}