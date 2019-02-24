package api.examples

/**
  * @ObjectName Checkpoint
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/19 22:20
  * @Version 1.0
  **/

/**
  * checkpoint 建立检查点。
  * 作用：将DAG中比较重要的中间数据做一个检查点将结果存储到一个高可用的地方(通常这个地方就是HDFS里面)
  */

import org.apache.spark.SparkContext

object Checkpoint {
  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Checkpoint Test")

    sc.setCheckpointDir("/Users/kngines/Documents/data/checkpoint")
    val a = sc.parallelize(1 to 9, 3)  // 从普通数组创建RDD, 包含1-9 9个数字，分别分布在3个分区中
    a.foreach(println)
    println(a.checkpoint)
    println(a.count)
  }
}
