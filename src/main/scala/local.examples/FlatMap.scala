package local.examples

/**
  * @ObjectName FlatMap
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/21 18:34
  * @Version 1.0
  **/
/**
  * map：对集合中每个元素进行操作。
  * flatMap：将函数应用于rdd之中的每一个元素，将返回的迭代器的所有内容构成新的rdd。通常用来切分单词。
  */

import org.apache.spark.SparkContext

object FlatMap {

  def main(args: Array[String]): Unit = {
    val sc = new SparkContext("local", "FlatMap Test")
    val data = Array[(String, Int)](("A", 1), ("B", 2),
      ("B", 3), ("C", 4),
      ("C", 5), ("C", 6)
    )
    val pairs = sc.makeRDD(data, 3)
    val result = pairs.flatMap(T => (T._1 + T._2))
    result.foreach(println)



  }

}
