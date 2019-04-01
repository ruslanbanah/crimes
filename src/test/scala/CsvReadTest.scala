import org.scalatest.FunSuite
import start.CSVSource
import scala.io.Source

class CsvReadTest extends FunSuite {
  test("Read CSV files : ") {
    val path = System.getProperty("user.dir") + "/src/test/scala/test.csv"
    val source = Source.fromFile(path)
    val res = CSVSource(source)
    println(res)
    assert(res.size == 2)
    assert(res(0).contains("-2.509126"))
    assert(res(0).contains("51.416137"))

    assert(res(1).contains("-2.497930"))
    assert(res(1).contains("51.417966"))
  }
}
