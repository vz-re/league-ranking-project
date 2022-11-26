package league.ranking.config

import com.typesafe.config.{Config, ConfigFactory}
import scala.util.Try

object Configuration {

  private val config: Config = ConfigFactory.load()

  lazy val WIN_POINTS: Int = Try(config.getInt("points.WIN_POINTS")).getOrElse(3)
  lazy val LOSE_POINTS: Int = Try(config.getInt("points.LOSE_POINTS")).getOrElse(0)
  lazy val DRAW_POINTS: Int = Try(config.getInt("points.DRAW_POINTS")).getOrElse(1)

}
