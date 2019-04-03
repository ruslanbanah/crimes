import org.scalatest.FunSuite
import start.CrimeCSVReader

class TopCrimesTest extends FunSuite {
  test("Get top : ") {
    val path = System.getProperty("user.dir") + "/src/test/scala/"
    val res = new CrimeCSVReader(path).topN(2)
    assert(res.size == 2)
    assert(res(0)._2.size == 39 && res(0)._2(0).long == "-2.595371" && res(0)._2(0).lat == "51.525158")
    assert(res(1)._2.size == 29 && res(1)._2(0).long == "-2.597084" && res(1)._2(0).lat == "51.449989")

    val res1 = new CrimeCSVReader(path).topN(4)
    assert(res1.size == 4)
    assert(res1(0)._2.size == 39 && res1(0)._2(0).long == "-2.595371" && res1(0)._2(0).lat == "51.525158")
    assert(res1(1)._2.size == 29 && res1(1)._2(0).long == "-2.597084" && res1(1)._2(0).lat == "51.449989")
    assert(res1(2)._2.size == 24 && res1(2)._2(0).long == "-2.590766" && res1(2)._2(0).lat == "51.457808")
    assert(res1(3)._2.size == 22 && res1(3)._2(0).long == "-2.530257" && res1(3)._2(0).lat == "51.470092")
  }
}
