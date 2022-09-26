import scala.io.Source

object Main {
  def main(args: Array[String]): Unit = {
    val moviesFile = Source.fromFile("data/tp1/movies.csv")
    val lines = moviesFile.getLines().toList
    val movies = lines
      .drop(1)
      .map(line => line.split("\t").toList)
      .map(movie => (movie(0), movie(1), movie(3), movie(5), movie(7), movie(8)))

    println(movies.mkString("\n"))
    moviesFile.close()
  }
}