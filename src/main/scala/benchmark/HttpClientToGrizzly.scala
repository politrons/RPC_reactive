package benchmark

import com.politrons.grpc.benchmark.BenchmarkUtils._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.Await

/**
  * Created by pabloperezgarcia on 08/04/2017.
  *
  * A really easy way to implement a client without almost any code
  * The Service class will receive and response a Future[Response] the type that you specify
  * Service[Req,Rep]
  */
object HttpClientToGrizzly {

  def run() = {
    val client: Service[Request, Response] = Http.newService("localhost:1982")
    makeRequests(client)
  }

  private def makeRequests(client: Service[Request, Response]) = {
    1 to requestNumber foreach (_ => {
      val request = http.Request(http.Method.Post, "/grizzly")
      request.setContentString("hello world")
      request.host("localhost:1982")
      val response = client(request)
      Await.result(response)
    })
  }
}
