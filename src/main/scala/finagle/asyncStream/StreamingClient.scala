package finagle.asyncStream

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.{Http, http}
import com.twitter.io.{Buf, Reader}

/**
  * Created by pabloperezgarcia on 08/04/2017.
  *
  * A really easy way to implement a client without almost any code
  * The Service class will receive and response a Future[Response] the type that you specify
  * Service[Req,Rep]
  */
object StreamingClient extends App {

  var start = 0l

  StreamingServer.start(8080)
  run(8080)
  var messageCount = 0 // Wait for 1000 messages then shut down.

  def run(port: Int) = {
    val client = Http.client.withStreaming(enabled = true).newService(s"/$$/inet/localhost/$port")

    val openStreamRequest = http.Request(http.Method.Get, "/start")

    client(openStreamRequest).flatMap {
      response =>
        response.write(Buf.Utf8("go it"))
        fromReader(response.reader).foreach {
          case Buf.Utf8(buf) =>
            println(messageCount)
            messageCount += 1
            if (messageCount >= 5000) {
              println(s"Time after consumed $messageCount:${System.currentTimeMillis() - start}")
              client.close()
            }
        }
    }
    Thread.sleep(2000)
    start = System.currentTimeMillis()
    val request = http.Request(http.Method.Post, "/talk")
    0 to 6000 foreach { index =>
      request.setContentString(s"hello world $index")
      client(request)
    }
    Thread.sleep(10000000)
  }

  def fromReader(reader: Reader): AsyncStream[Buf] =
    AsyncStream.fromFuture(reader.read(Int.MaxValue)).flatMap {
      case None => AsyncStream.empty
      case Some(a) => a +:: fromReader(reader)
    }
}
