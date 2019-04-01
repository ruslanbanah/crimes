import scala.io.Source
import java.io.File

def getListOfFiles(dir: String):List[File] = new File(dir).listFiles.filter(_.isFile).toList.filter(_.getName.endsWith("csv"))

def CSVSource(source:Source, delimiter:String = ","): List[List[String]] = {
  val MasterList = source.getLines().toList map {_.split("""\""" + delimiter).toList}
  MasterList.filter(_.head.nonEmpty)
}

def readAllFiles(absPath: String): List[List[String]] = {
  var result = List[List[String]]()
  var files = getListOfFiles(absPath)
  for ((file) <- files) {
    print(file.getPath())
    val source = Source.fromFile(file.getPath())
    result = result ::: CSVSource(source)
    source.close()
    println(" ... Complete.")
  }
  result
}

var res = readAllFiles("crimes/")
println(res.length)

var grouped = res.groupBy(_.slice(4,6).mkString(","))
println(res.length)