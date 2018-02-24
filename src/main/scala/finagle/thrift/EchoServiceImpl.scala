package finagle.thrift

import com.twitter.util.Future

class EchoServiceImpl extends EchoService[Future] {
  def echo(req: String): Future[String] = {
    println("get request: " + req)
    Future.value(req)
  }
}