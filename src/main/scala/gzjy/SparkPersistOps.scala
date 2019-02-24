package gzjy

/**
  * @ObjectName SparkPersistOps
  * @Description TODO Spark RDD的持久化，持久化策略的选择
  * @Author kngin
  * @Date: 2019/2/18 23:58
  * @Version 1.0
  **/

import org.apache.log4j.{Level, Logger}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object SparkPersistOps {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName(SparkPersistOps.getClass.getSimpleName())
    val sc = new SparkContext(conf)
    Logger.getLogger("org.apache.spark").setLevel(Level.OFF)
    Logger.getLogger("org.apache.hadoop").setLevel(Level.OFF)

    var start = System.currentTimeMillis()
    val linesRDD = sc.textFile("C:/Users/kngin/Desktop/123.txt")
    //    linesRDD.cache()
    //    linesRDD.persist(StorageLevel.MEMORY_ONLY)

    // 执行第一次RDD的计算
    val retRDD = linesRDD.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
    //    retRDD.cache()
    //    retRDD.persist(StorageLevel.DISK_ONLY)
    println(retRDD.count())
    println("第一次计算消耗的时间：" + (System.currentTimeMillis() - start) + "ms")

    // 执行第二次RDD的计算
    start = System.currentTimeMillis()
    println(retRDD.count())
    println("第二次计算消耗的时间：" + (System.currentTimeMillis() - start) + "ms")

    // 持久化使用结束之后，要想卸载数据
    // linesRDD.unpersist()

    sc.stop()

    //  第一次计算消耗的时间：2640ms
    //  第二次计算消耗的时间：49ms

  }
}