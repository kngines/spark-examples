package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName MapPartitionsRDDTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 14:05
  * @Version 1.0
  **/

/**
  * local:本地
  *
  * local[K]:K个线程进行并行运算
  *
  * Scala的解释器在解析函数参数(function arguments)时有两种方式：
  *
  *   传值调用（call-by-value）：先计算参数表达式的值，再应用到函数内部；
  *
  *   传名调用（call-by-name）：将未计算的参数表达式直接应用到函数内部
  *
  * MapPartitions:一个task仅仅会执行一次function，function一次接收所有的partition数据。
  *
  *   只要执行一次就可以，性能比较高。
  *
  *   如果在map过程中需要频繁创建额外的对象，则mapPartitions效率比map高的多。
  *
  * 缺点：
  *   普通的map操作通常不会导致内存的OOM异常。
  *
  *   MapPartitions：对于大量数据，比如一个partition，100万数据，一次传入一个function后，可能一下子内存不够，但又没有办法去腾出内存空间来，可能就OOM，内存溢出。
  *
  */
object MapPartitionsRDDTest {

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "MapPartitionsRDD Test")
    val data = Array[(String, Int)](("A1", 1), ("A2", 2),
      ("B1", 1), ("B2", 4),
      ("C1", 3), ("C2", 4)
    )
    val pairs = sc.parallelize(data, 3)
    val finalRDD = pairs.mapPartitions(iter => iter.filter(_._2 >= 2))

    // 打印输出
    finalRDD.collect.foreach(println)
  }
}
