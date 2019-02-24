package local.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName PipedRDDTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 16:18
  * @Version 1.0
  **/
/**
  * pipe方法: 对rdd进行管道操作。pipe输入一个cmd指令，然后在外部执行。
  *
  */
object PipedRDDTest {

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "Cartesian Test")
    val data1 = Array[(String, Int)](("K1", 1), ("K2", 2),
      ("U1", 3), ("U2", 4),
      ("W1", 3), ("W2", 4)
    )
    val pairs = sc.parallelize(data1, 3)

    val finalRDD = pairs.pipe("grep 2")
    //    val scriptPath = "/demo/echo.sh"
    //    val pipeRDD = dataRDD.pipe(scriptPath)

    finalRDD.foreach(println)

  }
}