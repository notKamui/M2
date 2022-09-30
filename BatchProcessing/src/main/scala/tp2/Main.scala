package tp2

import org.apache.log4j.{Level, LogManager, Logger}
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks.{break, breakable}

object Main {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    Logger.getLogger("spark").setLevel(Level.OFF)
    LogManager.getRootLogger.setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("TP2").setMaster("local[2]").set("spark.executor.memory", "1g")
    val sc = new SparkContext(conf)

    val triples = sc.parallelize(Array((1, 0, 5), (5, 1, 8), (8, 2, 1), (2, 0, 6), (3, 0, 6), (6, 1, 9), (5, 1, 9), (9, 3, 11), (9, 4, 12), (4, 0, 7), (7, 1, 9), (7, 2, 10), (14, 1, 15),
      (15, 1, 16), (14, 1, 16), (17, 0, 18), (18, 0, 19), (19, 1, 20), (20, 0, 17)))

    val soPairs = triples.map { case (s, _, o) => (s, o) }
    println("soPairs: " + soPairs.collect().mkString(", "))
    println("soPairs size " + soPairs.count())

    val subjects = soPairs.map { case (s, _) => s }
    val objects = soPairs.map { case (_, o) => o }

    val roots = subjects.subtract(objects).distinct()
    println("roots " + roots.collect().mkString(", "))
    val leaves = objects.subtract(subjects).distinct()
    println("leaves " + leaves.collect().mkString(", "))

    var transitiveClosure = soPairs
    var size = 0L
    var previousSize = 0L
    do {
      previousSize = transitiveClosure.count()
      val inter = transitiveClosure.map { case (s, o) => (o, s) }.join(transitiveClosure)
      transitiveClosure = transitiveClosure.union(inter.map { case (_, pair) => pair }).distinct()
      size = transitiveClosure.count()
    } while (size != previousSize)
    println("transitive closure " + transitiveClosure.collect().mkString(", "))
    println("transitive closure size " + transitiveClosure.count())

    val xor = transitiveClosure.subtract(soPairs).sortByKey()
    println("xor " + xor.collect().mkString(", "))
    println("xor size " + xor.count())

    val notRoots = subjects.subtract(roots).map(s => (s, 0))
    val rooted = transitiveClosure.subtractByKey(notRoots).groupByKey().map { case (s, os) => (s, os.toList.sorted) }
    println("rooted " + rooted.collect().mkString(", "))
    println("rooted size " + rooted.count())

    val transitiveClosure2 = sc.parallelize(List((1,1), (2, 2), (2, 3), (2, 1), (1, 2)))
    
    val candidates = transitiveClosure2.filter { case (s, o) => s == o }
    val subjectP = subjects.map(s => (s, 0)).subtractByKey(candidates)
    val a = candidates.join(transitiveClosure2.subtractByKey(subjectP))
    println("candidates " + a.collect().mkString(", "))

  }
}
