package league.ranking

import league.ranking.ProcessFunctions._
import java.io.{FileNotFoundException, IOException}
import scala.io.StdIn.readLine
import scala.collection.mutable
import scala.io.{BufferedSource, Source}

object RankingTable extends App {
  val rankingMap = mutable.HashMap[String, Int]()

  try {
    println("Please enter filepath:")
    val inputPath = readLine()

    val bs: BufferedSource = Source.fromFile(inputPath)
    for (line <- bs.getLines()) {
      val splitEntry = splitLineEntry(line)
      val matchInfo = getMatchInfo(splitEntry)
      evaluateMatch(rankingMap, matchInfo)
    }
    bs.close()

    val sortedTeamRanks = convertAndSort(rankingMap)
    val rankOutput = constructOutput(sortedTeamRanks)

    println("Save result in output file: y/n")
    val saveFile = readLine()

    saveFile match {
      case "y" =>
        println("Please enter output filename:")
        val outputFilename = readLine()
        writeOutputFile(outputFilename + ".txt", rankOutput)
      case _ =>
        for (teamRank <- rankOutput) {
          println(teamRank)
        }
    }
  }
  catch {
    case e: FileNotFoundException => println("Couldn't find that file: " + e)
    case e: IOException => println("Got an IOException: " + e)
    case e: Exception => println("Exception occurred: " + e)
  }

}
