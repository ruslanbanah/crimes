import java.io.File
import org.scalatest.FunSuite
import start.CrimeCSVReader

class CsvReadTest extends FunSuite {
  test("Read CSV files : ") {
    val path = System.getProperty("user.dir") + "/src/test/scala/test.csv"
    val res = new CrimeCSVReader(path).readCrimes(path)
    assert(res.size == 9385)
    assert(res(0).lat.contains("51.416137"))
    assert(res(0).long.contains("-2.509126"))
    assert(res(1).lat.contains("51.409343"))
    assert(res(1).long.contains("-2.514442"))
  }
}
