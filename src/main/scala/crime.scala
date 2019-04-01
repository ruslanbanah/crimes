import scala.io.Source
import java.io.File

object start {
  def getListOfFiles(dir: String): List[File] = {
    val listFiles = new File(dir).listFiles
    val res = listFiles.filter(_.isFile).toList.filter(_.getName.endsWith("csv"))
    res
  }

  def getColls(row: String): List[String] = {
    row.split(",").zipWithIndex.filter { case (v, index) => (List(0, 4, 5, 9) contains index) && v.nonEmpty }.drop(1).map(_._1).map(_.trim).toList
  }

  def CSVSource(source: Source): List[List[String]] = {
    val rows = source.getLines().toList.drop(1).map(getColls)
    rows.filter(_.length == 3)
  }

  def top(events: List[List[String]], num: Int): Seq[(String, List[scala.List[String]])] = {
    events.groupBy(_.slice(0,2).mkString(",")).toSeq.sortBy(_._2.size).reverse.slice(0,num)
  }

  def getThefts(row: List[List[String]]):List[String] = {
    val pattern = "(T|t)heft".r
    row.map(_.slice(2,3).toString).distinct.filter(s => pattern.findFirstIn(s).isDefined)
  }

  def readAllFiles(absPath: String): List[List[String]] = {
    var result = List[List[String]]()
    val files = getListOfFiles(absPath)
    for (file <- files) {
      print("Load: ", file.getPath)
      val source = Source.fromFile(file.getPath)
      result = result ::: CSVSource(source)
      source.close()
      println(" ... Complete.")
    }
    result
  }
  def main(args: Array[String]): Unit = {
    val defaultPath = System.getProperty("user.dir") + "/src/main/scala/crimes"
    val path = if(args.headOption.nonEmpty) args(0) else defaultPath
    println("Path : " + path)
    val events = readAllFiles(path)
    println("Events count: ", events.length)
    val results = top(events, 5)
    results.foreach(row => {
      val thefts = getThefts(row._2)
      println("----------------------------")
      println(s"(${row._1}): ${row._2.size}")
      println("Thefts:")
      thefts.foreach(s => println(s.toString))
    })
  }
}
