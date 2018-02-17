
import com.twitter.finagle.{Failure, http, _}
import com.twitter.util.Future

/**
  * Created by pabloperezgarcia on 08/04/2017.
  * The Service class will receive and response a Future[Response] with types that you specify
  * Service[Req,Rep]
  */
object FinagleService {

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      val value = req.getContentString()
      val res = req.getResponse()
      res.setContentString(value)
      Future.value(res)
    }
  }
}
