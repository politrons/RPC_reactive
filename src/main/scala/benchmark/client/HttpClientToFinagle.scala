package benchmark.client

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service, http}
import com.twitter.util.Await
import finagle.BenchmarkUtils._

/**
  * Created by pabloperezgarcia on 08/04/2017.
  *
  * A really easy way to implement a client without almost any code
  * The Service class will receive and response a Future[Response] the type that you specify
  * Service[Req,Rep]
  */
object HttpClientToFinagle {

  def run(port:Int) = {
    val client: Service[Request, Response] = Http.newService(s"localhost:$port")
    makeRequests(client)
  }

  private def makeRequests(client: Service[Request, Response]) = {
    1 to requestNumber foreach (_ => {
      val request = http.Request(http.Method.Post, "/")
      request.setContentString(getPayload)
      val response = client(request)
      Await.result(response)
    })
  }
}
