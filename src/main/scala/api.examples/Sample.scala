package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName Sample
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 22:52
  * @Version 1.0
  **/
/**
  * foreach:
  * 为每个数据执行一次操作
  * 参数说明：
  *
  * withReplacement ：取样后是否将元素放回。
  * true:元素放回 ，返回的子集会有重复，可以被多次抽样；
  * false:元素不放回 ，返回的子集没有重复
  * fraction：期望样本的大小作为RDD大小的一部分，
  *
  * seed：随机数生成器的种子
  */
object Sample {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "Sample Test")
    val d = sc.parallelize(1 to 100, 10)

    val result1 = d.sample(false, 0.1, 0)
    val result2 = d.sample(true, 0.1, 0)

    println(result1.toDebugString)

    println("result 1:")
    result1.collect().mkString(",").map(print)
    // 10,27,30,39,55,63,82,99

    println("\nresutl 2:")
    //    result2.collect.foreach(x => print(x + " "))
    //    println()
    result2.collect().mkString(",").map(print)
    println()
    // 10,27,30,40,55,63,82,99
    // foreachWith (Deprecated)
  }
}