import org.scalatest.FunSuite
import start.getColls
class ColumnsReadTest extends FunSuite{
  test("Columns read: ") {
    val testRow = "idColl,coll1,coll2,coll3,coll4,coll5,coll6,coll7,coll8,coll9"
    val res = getColls(testRow)

    assert(res.contains("coll4"))
    assert(res.contains("coll5"))
    assert(res.contains("coll9"))

    assert(!res.contains("coll0"))
    assert(!res.contains("coll1"))
    assert(!res.contains("coll2"))
    assert(!res.contains("coll3"))
    assert(!res.contains("coll6"))
    assert(!res.contains("coll7"))
    assert(!res.contains("coll8"))

  }
}
