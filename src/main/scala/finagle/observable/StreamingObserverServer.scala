package finagle.observable

import java.util.concurrent.TimeUnit

import com.twitter.finagle.http.{Response, Status}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.io.{Buf, Reader}
import com.twitter.util.Future
import rx.Observable

object StreamingObserverServer {


  def start(port: Int) {
    Http.server
      .withStreaming(enabled = true)
      .serve(s"0.0.0.0:$port", service)
  }

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      req.path match {
        case "/" =>
          val writable = Reader.writable()
          Observable.interval(1, TimeUnit.SECONDS)
            .doOnNext(i => writable.write(Buf.Utf8(s"Server ping $i")))
            .subscribe()
          Future.value(Response(req.version, Status.Ok, writable))
      }
    }
  }


}
