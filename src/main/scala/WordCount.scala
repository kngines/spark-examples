import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args: Array[String]): Unit = {
    val testFile = "C:\\Users\\kngin\\Desktop\\Hive使用说明.txt"
    val conf =  new SparkConf().setAppName("Sample Application").setMaster("local")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(testFile)

    val wordcount = rdd.flatMap(_.split(" ")).map((_,1)).reduceByKey(_ + _).map(x => (x._2, x._1)).sortByKey(false).map(x => (x._2,x._1))
    wordcount.saveAsTextFile("C:\\Users\\kngin\\Desktop\\tongjijieguo.txt")
    sc.stop()
  }
}