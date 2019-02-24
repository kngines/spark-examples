package local.examples

import org.apache.spark.{RangePartitioner, SparkContext}

/**
  * @ObjectName MapValuesTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 14:21
  * @Version 1.0
  **/

/**
  * mapValues: 对键值对每个value都应用一个函数，但是，key不会发生变化。
  *
  * reduce()与fold()方法是对同种元素类型数据的RDD进行操作，即必须同构，其返回值返回一个同样类型的新元素
  *
  * partitionBy: 根据partitioner函数生成新的ShuffleRDD，将原RDD重新分区。
  */
object MapValuesTest {
  def main(args: Array[String]) {

    val sc = new SparkContext("local", "ReduceByKeyToDriver Test")
    val data1 = Array[(String, Int)](("K", 1), ("T", 2),
      ("T", 3), ("W", 4),
      ("W", 5), ("W", 6)
    )
    val pairs = sc.parallelize(data1, 2)
    val result1 = pairs.reduce((A, B) => (A._1 + "#" + B._1, A._2 + B._2))
    val result2 = pairs.fold(("K0", 10))((A, B) => (A._1 + "#" + B._1, A._2 + B._2))
    val result3 = pairs.partitionBy(new RangePartitioner(2, pairs, true))
    val result4 = pairs.mapValues(V => 10 * V)

    println("---- reduce ----")
    println(result1) // (K#T#T#W#W#W,21)
    println("---- fold ----")
    println(result2) // (K0#K0#K#T#T#K0#W#W#W,51) 30（与分区数量有关） + 21 = 51
    println("---- partitionBy ----")
    result3.take(200).foreach(println)
    // output
    //    (K,1)
    //    (T,2)
    //    (T,3)
    //    (W,4)
    //    (W,5)
    //    (W,6)
    println("---- mapValues ----")
    result4.take(200).foreach(println)
    // output
    //    (K,10)
    //    (T,20)
    //    (T,30)
    //    (W,40)
    //    (W,50)
    //    (W,60)

  }
}