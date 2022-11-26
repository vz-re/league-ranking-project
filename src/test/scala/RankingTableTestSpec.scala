import league.ranking.MatchInfo
import league.ranking.ProcessFunctions._
import league.ranking.config.Configuration._
import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable

class RankingTableTestSpec extends AnyFunSuite {
  val inputLine: String = "A 3,B 3"
  val teamsInfo: Vector[String] = Vector("A 3", "B 3")
  val matchInfo: MatchInfo = MatchInfo("A", 3, "B", 3)

  test("splitLineEntry function should separate strings based on a comma") {
    assert(splitLineEntry(inputLine) === teamsInfo)
    assert(splitLineEntry("A 3;B 3") !== teamsInfo)
    assert(splitLineEntry("A 3 B 3") !== teamsInfo)
  }

  test("getMatchInfo function should separate strings at last whitespace and save result in a MatchInfo case class") {
    assert(getMatchInfo(teamsInfo) === matchInfo)
    assert(getMatchInfo(Vector("AB A 3", "CE FR TR 3")) === MatchInfo("AB A", 3, "CE FR TR", 3))
  }

  test("updateRankingMap function should create and update a Map according to stipulated rules") {
    val createMap = updateRankingMap(mutable.HashMap.empty[String, Int], "A", 3, "B", 0)
    assert(createMap === mutable.HashMap("A" -> 3, "B" -> 0))
    assert(updateRankingMap(createMap, "C", 1, "B", 1) === mutable.HashMap("A" -> 3, "B" -> 1, "C" -> 1))
  }

  test("getResult function should assign points to each team based on their final game score") {
    assert(getResult(2, 2) === (DRAW_POINTS, DRAW_POINTS))
    assert(getResult(3, 1) === (WIN_POINTS, LOSE_POINTS))
    assert(getResult(2, 6) === (LOSE_POINTS, WIN_POINTS))
  }

  test("evaluateMatch function should evaluate each match and update the Map using updateRankingMap") {
    assert(evaluateMatch(mutable.HashMap("A" -> 3, "B" -> 0), MatchInfo("E", 3, "C", 3)) ===
      mutable.HashMap("A" -> 3, "B" -> 0, "C" -> 1, "E" -> 1))
  }

  val rankingMap: mutable.HashMap[String, Int] = mutable.HashMap("AB" -> 3, "CD" -> 1, "BC" -> 0, "EF" -> 1, "DE" -> 4)
  val sortedRankings: Vector[(String, Int)] = Vector(("DE", 4), ("AB", 3), ("CD", 1), ("EF", 1), ("BC", 0))

  test("convertAndSort function should convert the Map to a Vector and sort it in descending order then alphabetically") {
    assert(convertAndSort(rankingMap) === sortedRankings)
    assert(convertAndSort(rankingMap) !== Vector(("DE", 4), ("AB", 3), ("EF", 1), ("CD", 1), ("BC", 0)))
    assert(convertAndSort(rankingMap) !== Vector(("BC", 0), ("CD", 1), ("EF", 1), ("AB", 3), ("DE", 4)))
  }

  test("constructOutput function should shape the output for the ranking table") {
    assert(constructOutput(sortedRankings) === Vector("1. DE, 4 pts", "2. AB, 3 pts", "3. CD, 1 pt", "4. EF, 1 pt", "5. BC, 0 pts"))
    assert(constructOutput(sortedRankings) !== Vector("1. DE, 4 pts", "2. AB, 3 pts", "3. CD, 1 pts", "4. EF, 1 pts", "5. BC, 0 pts"))
  }

}
