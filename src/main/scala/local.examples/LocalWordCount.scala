package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName LocalWordCount
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 13:56
  * @Version 1.0
  **/

object LocalWordCount {
  def main(args: Array[String]) {

    val sc = new SparkContext("local[4]", "LocalWordCount")
    val myFile = sc.textFile("E:\\SparkDev\\datas\\generaterandomtext\\randomText-10MB.txt")

    val wordAndCount = myFile.flatMap(s => s.split(" "))
      .map(w => (w, 1))
    val result = wordAndCount.reduceByKey(_ + _) // rdd.reduceByKey((x,y) => x + y)
    println("文件行数据量: " + wordAndCount.count()) // 927386
    println("文件Key数据量: " + result.count()) // 1000
    result.foreach(println)
  }

}