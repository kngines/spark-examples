package api.examples

import org.apache.spark.SparkContext

/**
  * @ObjectName IntersectionTest
  * @Description TODO
  * @Author kngin
  * @Date: 2019/2/24 22:36
  * @Version 1.0
  **/

/**
  * union:
  *
  *   将两个RDD进行合并，不去重。
  *
  * intersection:
  *
  *   返回两个RDD的交集，并且去重。
  *
  * subtract:
  *
  *   返回在RDD中出现，并且不在otherRDD中出现的元素，不去重
  *
  * 通过 toDebugString 方法来查看RDD的谱系。
  */
object IntersectionTest {

  def main(args: Array[String]) {

    val sc = new SparkContext("local", "Intersection Test")
    val a = sc.parallelize(List(1, 2, 3, 3, 4, 5), 3)
    val b = sc.parallelize(List(1, 2, 5, 6), 2)

    val r = a.intersection(b)
    println(r.map(x => x + ", ").collect().foreach(print))
    println(r.toDebugString)

    //    1, 5, 2, ()
    //    (3) MapPartitionsRDD[7] at intersection at IntersectionTest.scala:21 []
    //    |  MapPartitionsRDD[6] at intersection at IntersectionTest.scala:21 []
    //    |  MapPartitionsRDD[5] at intersection at IntersectionTest.scala:21 []
    //    |  CoGroupedRDD[4] at intersection at IntersectionTest.scala:21 []
    //    +-(3) MapPartitionsRDD[2] at intersection at IntersectionTest.scala:21 []
    //    |  |  ParallelCollectionRDD[0] at parallelize at IntersectionTest.scala:17 []
    //    +-(2) MapPartitionsRDD[3] at intersection at IntersectionTest.scala:21 []
    //    |  ParallelCollectionRDD[1] at parallelize at IntersectionTest.scala:18 []
  }
}