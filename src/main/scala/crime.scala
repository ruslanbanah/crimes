import scala.io.Source
import java.io.File

object start {
  trait CrimesReader {
    def readCrimes(fileName: String): Seq[Crime]
    def readAllCSVFiles(): Seq[Crime]
    def getListOfFiles(): List[String]
    def topN(n: Int): Seq[(String, Seq[start.Crime])]
  }
  case class Crime(coordinate: String, crimeType: String)

  class CrimeCSVReader(val dir: String) extends CrimesReader {
    override def readCrimes(fileName: String): Seq[Crime] = {
      for {
        line <- Source.fromFile(fileName).getLines().drop(1).toVector
        values = line.split(",").map(_.trim) if values(0).nonEmpty && values(4).nonEmpty && values(5).nonEmpty
      } yield Crime(s"${values(4)}, ${values(5)}", values(9))
    }
    override def readAllCSVFiles(): Seq[Crime] = {
      getListOfFiles().flatten(readCrimes).toVector
    }
    override def getListOfFiles(): List[String] = {
      new File(dir).listFiles.filter(_.isFile).toList.filter(_.getName.endsWith("csv")).map(_.getAbsolutePath)
    }
    override def topN(n: Int): Seq[(String, Seq[start.Crime])] = {
      readAllCSVFiles().groupBy(_.coordinate).toSeq.sortBy(_._2.size).reverse.slice(0, n)
    }
  }

  def printResult(row: (String, scala.Seq[start.Crime])): Any = {
    val thefts = row._2.map(_.crimeType).filter("(T|t)heft".r.findFirstIn(_).isDefined).distinct
    println("----------------------------")
    println(s"(${row._1}): ${row._2.size}")
    println("Thefts:")
    thefts.foreach(println)
  }
  def main(args: Array[String]): Unit = {
    val defaultPath = System.getProperty("user.dir") + "/src/main/scala/crimes"
    val path = if(args.headOption.nonEmpty) args(0) else defaultPath
    new CrimeCSVReader(path).topN(5).foreach(printResult)
  }
}
