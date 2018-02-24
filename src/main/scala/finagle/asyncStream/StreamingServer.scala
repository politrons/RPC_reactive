package finagle.asyncStream

import com.twitter.concurrent.AsyncStream
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.io.{Buf, Reader}
import com.twitter.util.{Await, Future}

object StreamingServer {

  val writable = Reader.writable()

  def start(port: Int) {
    Http.server
      .withStreaming(enabled = true)
      .serve(s"0.0.0.0:$port", service)
  }

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      req.path match {
        case "/start" => {
          Future.value(Response(req.version, Status.Ok, writable))
        }
        case "/talk" =>
          val buf = Buf.Utf8(req.getContentString())
          Future {
            writable.write(buf)
          }
          val response = Response()
//          response.setChunked(true)
          Future.value(response)
      }
    }
  }


}
