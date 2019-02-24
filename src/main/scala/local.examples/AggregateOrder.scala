package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName AggregateOrder
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/23 17:30
  * @Version 1.0
  **/
object AggregateOrder {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "AggregateOrder Test")
    val data = List("12", "23", "345", "4567")

    val pairs = sc.parallelize(data, 2)
    pairs.foreach(x => println(x.length))

    //val result = pairs.aggregate("")((x,y) => math.min(x.length, y.length).toString, (x,y) => x + y)

    val result2 = pairs.aggregate("")((x,y) => "[" + x.length + "," + y.length + "] ", (x,y) => x + y)

//    result2.foreach(println)
    println(result2)

    val result3 = pairs.aggregate("")((x,y) => "[" + x + "," + y + "] ", (x,y) => x + y)
    println(result3)
    println(result3.length)  // "[[,12] ,23] [[,345] ,4567] " 字符串长度 27

  }
}