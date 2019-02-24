package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName CountApproxDistinct
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 22:13
  * @Version 1.0
  **/

/**
  * countApproxDistinct（jdz)
  *
  *   计算去重后的值的大约个数
  *   jdz： 该参数是用来规定值之间的相似度（精度值），值与值之间的相似度（精度）达到 jdz, 则将其看作是一样的值。
  *   jdz越小说明值与值之间越相似。控制在 0到0.4（不包括）之间
  */
object CountApproxDistinct {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "CountApproxDistinct Test")

    val a = sc.parallelize(1 to 10000, 20)
    val b = a ++ a ++ a ++ a ++ a
    println("b RDD中元素个数: " + b.count())
    println("b RDD中元素个数(去重后): " + b.distinct().count())

    val result = b.countApproxDistinct(0.01)
    println("countApproxDistinct 计数: " + result)

    // output
    //    b RDD中元素个数: 50000
    //    b RDD中元素个数(去重后): 10000
    //    countApproxDistinct 计数: 8224
  }
}