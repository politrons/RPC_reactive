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
object HttpClient {

  def run() = {
    val requestNumber = 1 to 5000 toList
    val client: Service[Request, Response] = Http.newService("localhost:1982")
    makeRequests(client, requestNumber)
  }


  private def makeRequests(client: Service[Request, Response], requestNumber: List[Int]) = {
    requestNumber.foreach(_ => {
      val request = http.Request(http.Method.Post, "/grizzly")
      request.setContentString("hello world")
      request.host("localhost:1982")
      val response = client(request)
      Await.result(response)
    })
  }
}
