package league.ranking

import java.io.{BufferedWriter, File, FileWriter}
import scala.collection.mutable

object ProcessFunctions {

  def splitLineEntry(line: String): Vector[String] = {
    line.split(",").toVector
  }

  def getMatchInfo(teamsInfo: Vector[String]): MatchInfo = {
    val split = teamsInfo.map(ti => {
      val spl = ti.split("\\s(?=\\S*$)")
      (spl(0).trim, spl(1).toInt)
    })
    MatchInfo(split.head._1, split.head._2, split.last._1, split.last._2)
  }

  def updateRankingMap(map: mutable.HashMap[String, Int], team1: String, team1Points: Int, team2: String, team2Points: Int): map.type = {
    map += ((team1, map.getOrElse(team1, 0) + team1Points),
      (team2, map.getOrElse(team2, 0) + team2Points))
  }

  def evaluateMatch(map: mutable.HashMap[String, Int], matchInfo: MatchInfo): map.type = {
    (matchInfo.team1Score, matchInfo.team2Score) match {
      case (a, b) if a > b => updateRankingMap(map, matchInfo.team1, 3, matchInfo.team2, 0)
      case (a, b) if a < b => updateRankingMap(map, matchInfo.team2, 3, matchInfo.team1, 0)
      case (a, b) if a == b => updateRankingMap(map, matchInfo.team1, 1, matchInfo.team2, 1)
    }
  }

  def convertAndSort(map: mutable.HashMap[String, Int]): Vector[(String, Int)] = {
    map.toVector.sortBy {
      case (k, v) => (-v, k)
    }
  }

  def constructOutput(sortedTeams: Vector[(String, Int)]): Vector[String] = {
    sortedTeams.foldLeft(1, Vector[String]()) {
      case ((serialPrefix, acc), team) =>
        val suffix = team._2 match {
          case 1 => "pt"
          case _ => "pts"
        }
        (serialPrefix + 1, s"${serialPrefix.toString}. ${team._1}, ${team._2} $suffix" +: acc)
    }._2.reverse
  }

  def writeOutputFile(filename: String, lines: Vector[String]): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    for (line <- lines) {
      bw.write(line)
      bw.newLine()
    }
    bw.close()
  }

}
