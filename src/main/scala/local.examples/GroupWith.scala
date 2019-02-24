package local.examples

import org.apache.spark.SparkContext
import org.spark_project.dmg.pmml.True

/**
  * @ObjectName GroupWith
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 12:59
  * @Version 1.0
  **/
object GroupWith {
  def main(args: Array[String]) {

    val sc = new SparkContext("local[2]", "GroupWith Test")

    val data1 = Array[(String, Int)](("A1", 1), ("A2", 2),
      ("B1", 3), ("B2", 4),
      ("C1", 5), ("C1", 6)
    )

    val data2 = Array[(String, Int)](("A1", 7), ("A2", 8),
      ("B1", 9), ("C1", 0)
    )
    val pairs1 = sc.parallelize(data1, 3)
    val pairs2 = sc.parallelize(data2, 2)


    val result = pairs1.groupWith(pairs2)
    println("---- print start ----")
    result.keys.map(x => x + ", ").sortBy(x => x, true).take(200).foreach(print)
    println()
    // output:
    //    A1, A2, B1, B2, C1,
    result.sortByKey().collect().foreach(println)

    // output:
    //    (A1,(CompactBuffer(1),CompactBuffer(7)))
    //    (A2,(CompactBuffer(2),CompactBuffer(8)))
    //    (B1,(CompactBuffer(3),CompactBuffer(9)))
    //    (B2,(CompactBuffer(4),CompactBuffer()))
    //    (C1,(CompactBuffer(5, 6),CompactBuffer(0)))

  }
}