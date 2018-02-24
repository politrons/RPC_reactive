package finagle.asyncStream

import com.twitter.concurrent.AsyncStream
import com.twitter.util.Future

class StreamClient {


  // Construction from a materialized value
  val result: String = "wonderful string" // materialized wonderful string
  val spool: AsyncStream[String] = AsyncStream.of(result)

  // Construction from a Future
  def getString(): Future[String] = Future {
    "wonderful string"
  }

  val unforced: AsyncStream[String] = AsyncStream.fromFuture(getString())


}
