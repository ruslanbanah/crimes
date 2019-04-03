import scala.io.Source
import java.io.File

object start {
  case class Crime(long: String, lat: String, crimeType: String)

  class CrimeCSVReader(val dir: String) {
    def readCrimes(fileName: String): Seq[Crime] = {
      for {
        line <- Source.fromFile(fileName).getLines().drop(1).toVector
        values = line.split(",").map(_.trim) if values(0).nonEmpty && values(4).nonEmpty && values(5).nonEmpty
      } yield Crime(values(4), values(5), values(9))
    }
    def readAllCSVFiles(): Seq[Crime] = {
      getListOfFiles().flatten(readCrimes).toVector
    }
    def getListOfFiles(): List[String] = {
      new File(dir).listFiles.filter(_.isFile).toList.filter(_.getName.endsWith("csv")).map(_.getAbsolutePath)
    }
    def topN(n: Int): Seq[((String, String), Seq[start.Crime])] = {
      readAllCSVFiles().groupBy(row => (row.long, row.lat)).toSeq.sortBy(-_._2.size).take(n)
    }
  }

  def printResult(row: ((String, String), scala.Seq[start.Crime])): Any = {
    val thefts = row._2.map(_.crimeType).filter("(T|t)heft".r.findFirstIn(_).isDefined).distinct
    println("----------------------------")
    println(s"${row._1}: ${row._2.size}")
    println("Thefts:")
    thefts.foreach(println)
  }
  def main(args: Array[String]): Unit = {
    val defaultPath = System.getProperty("user.dir") + "/src/main/scala/crimes"
    val path = if(args.headOption.nonEmpty) args(0) else defaultPath
    new CrimeCSVReader(path).topN(5).foreach(printResult)
  }
}
