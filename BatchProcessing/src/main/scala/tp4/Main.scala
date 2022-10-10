package tp4

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("spark").setLevel(Level.OFF)
    LogManager.getRootLogger.setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("TP2").setMaster("local[2]").set("spark.executor.memory", "1g")
    val sc = new SparkContext(conf)

    val univ = sc.textFile("data/tp4/univ1.nt")
      .map(_.split(" "))
      .map(x => (x(1), x(0), x(2)))
    val props = sc.textFile("data/tp4/univProps.txt")
      .map(_.split(" "))
      .map(x => (x(1), x(0)))
    val concepts = sc.textFile("data/tp4/univConcepts.txt")
      .map(_.split(" "))
      .map(x => (x(1), x(0)))

    println("univ size " + univ.count())
    println("concepts size " + concepts.count())
    println("props size " + props.count())

    // map subjects and predicates to props keys by value, and objects to concepts keys by value
    val subjects = univ.map(e => (e._1, e))
    val encodedProps = props.join(subjects).map { case (_, (v, (_, p, o))) => (v, p, o) }

    val uniqueUsedProps = encodedProps.map { case (v, _, _) => v }.distinct()
    println("unique used props " + uniqueUsedProps.count())

    val objects = encodedProps.map(e => (e._3, e))
    val encodedPropsAndConcepts = concepts.join(objects).map { case (_, (v, (s, p, _))) => (s, p, v) }

    println("encoded props and concepts " + encodedPropsAndConcepts.take(10).mkString("\n"))
  }
}
