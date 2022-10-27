package tp7

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("spark").setLevel(Level.OFF)
    LogManager.getRootLogger.setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("TP7").setMaster("local[2]").set("spark.executor.memory", "1g")
    val sc = new SparkContext(conf)

    val drugs = sc.textFile("data/tp7/med.txt")
      .map(_.split("\t"))
      .map(x => (x(0), x(1), x(6)))
    println("drugs count : " + drugs.count) // 13674

    val countByStatus = drugs.map(x => (x._3, 1)).reduceByKey(_ + _).collect
    println("count by status : ", countByStatus.mkString) // (Commercialis�e,11861) / (Non commercialis�e,1813)

    val comDrugs = drugs.filter(_._3 == "Commercialis�e").map(x => (x._1, x._2))
    println("comDrugs count : " + comDrugs.count) // 11861

    val sub = sc.textFile("data/tp7/compo.txt")
      .map(_.split("\t"))
      .map(x => (x(0), x(2), x(3), x(6)))
    println("substance count : " + sub.count) // 25484

    val countBySubSource = sub.map(x => (x._4, 1)).reduceByKey(_ + _).collect
    println("count by substance source : ", countBySubSource.mkString) // (FT,4463) / (SA,21021)

    val saSub = sub.filter(_._4 == "SA").map(x => (x._1, x._2, x._3))
    println("saSub count : " + saSub.count) // 21021

    val subCode = saSub.map(x => (x._1, x._2))
    println("subCode count : " + subCode.count) // 21021
    val subCodeDistinct = subCode.distinct
    println("subCodeDistinct count : " + subCodeDistinct.count) // 21021

    val saSubV = saSub.map(x => (x._2, x._3)).distinct
    println("saSubV count : " + saSubV.count) // 3356

    val vertices = sc.union(comDrugs, saSubV)
    println("vertices count : " + vertices.take(10).mkString) // 13674
  }
}
