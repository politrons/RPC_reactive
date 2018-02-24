package finagle.observable

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.{Http, http}
import com.twitter.io.{Buf, Reader}
import com.twitter.util.Await

object StreamingObservableClient extends App {

  var start = 0l

  StreamingObserverServer.start(8080)

  run(8080)

  def run(port: Int) = {
    val client = Http.client.withStreaming(enabled = true).newService(s"/$$/inet/localhost/$port")

    val openStreamRequest = http.Request(http.Method.Get, "/")

    Await.result(client(openStreamRequest).flatMap {
      response =>
        fromReader(response.reader).foreach {
          case Buf.Utf8(buf) =>
            println(buf)
        }
    })
  }

  def fromReader(reader: Reader): AsyncStream[Buf] =
    AsyncStream.fromFuture(reader.read(Int.MaxValue)).flatMap {
      case None => AsyncStream.empty
      case Some(a) => a +:: fromReader(reader)
    }
}
