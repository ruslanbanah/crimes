import org.scalatest.FunSuite
import start.CrimeCSVReader

class TopCrimesTest extends FunSuite {
  test("Get top : ") {
    val path = System.getProperty("user.dir") + "/src/test/scala/"
    val res = new CrimeCSVReader(path).topN(2)
    assert(res.size == 2)
    assert(res(0)._2.size == 39)
    assert(res(1)._2.size == 29)

    val res1 = new CrimeCSVReader(path).topN(4)
    assert(res1.size == 4)
    assert(res1(0)._2.size == 39)
    assert(res1(1)._2.size == 29)
    assert(res1(2)._2.size == 24)
    assert(res1(3)._2.size == 22)
  }
}
