package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName AggregateStatic
  * @Description TODO 聚合统计
  * @Author kngin
  * @Date: 2019/2/23 18:32
  * @Version 1.0
  **/

/**
  * Spark 为包含键值对类型的 RDD 提供了一些专有的操作，称为 pair RDD
  *
  * 错误提示: value reduceByKey is not a member of Array[(String, Int)]
  *
  *   原因: 使用scala编程时，没有使用pairRDD
  *
  * yield: 记录每次迭代中的有关值，并逐一存入到一个数组中
  */
object AggregateStatic {
  val sc = new SparkContext("local", "Aggregate Static Test")
  val ss = List(
    "A;B;C;D;B;D;C",
    "B;D;A;E;D;C",
    "A;B"
  )
  val rdd = sc.parallelize(ss)
  val mid_rdd = rdd.map(_.split(";")).flatMap(x => {
    for (i <- 0 until x.length - 1) yield (x(i) + "," + x(i + 1), 1)
  }).reduceByKey(_ + _).foreach(println)
}
