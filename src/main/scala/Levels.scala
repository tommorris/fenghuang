abstract class Level {
  val identifier: Int
}

object Exact extends Level { val identifier = 0 }
object Postal extends Level { val identifier = 1 }
object Neighbourhood extends Level { val identifier = 2 }
object City extends Level { val identifier = 3 }
object Region extends Level { val identifier = 4 }
object State extends Level { val identifier = 5 }
object Country extends Level { val identifier = 6 }