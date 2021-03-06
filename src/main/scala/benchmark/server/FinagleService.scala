package benchmark.server

import com.google.gson.Gson
import com.politrons.grpc.Pojo
import com.twitter.finagle.{http, _}
import com.twitter.util.Future

/**
  * Created by pabloperezgarcia on 08/04/2017.
  * The Service class will receive and response a Future[Response] with types that you specify
  * Service[Req,Rep]
  */
object FinagleService {

  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      val res = req.getResponse()
      val gson = new Gson()
      val pojo = gson.fromJson(res.getContentString(), classOf[Pojo])
      res.setContentString(gson.toJson(pojo))
      Future.value(res)
    }
  }
}
