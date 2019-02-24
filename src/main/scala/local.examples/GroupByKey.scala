package local.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

/**
  * @ObjectName GroupByKey
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/21 18:50
  * @Version 1.0
  **/

/**
  * groupByKey会将RDD[key,value] 按照相同的key进行分组，形成RDD[key,Iterable[value]]的形式
  *
  */
object GroupByKey {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "GroupByKey Test")
    val data = Array[(Int, Char)]((1, 'a'), (2, 'b'),
      (3, 'c'), (4, 'd'),
      (5, 'e'), (3, 'f'),
      (2, 'g'), (1, 'h')
    )

    val pairs = sc.parallelize(data, 3)
    val result = pairs.groupByKey(2)

    result.collect().foreach(println)
    println(result.toDebugString)
  }

}
