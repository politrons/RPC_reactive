package benchmark

import com.twitter.finagle._

/**
  * Created by pabloperezgarcia on 08/04/2017.
  *
  * Finagle provide multiple operators features on server side that could be handy
  * Such features as retry policy, error handler, max concurrent connections, timeout and so on.
  */
object HttpServers {

  private val port = "1983"

  def start(): Unit = {
   Http.server
      .serve(s"localhost:$port", FinagleService.service)
  }

}
