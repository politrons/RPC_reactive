package finagle.thrift.rpc

import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}

/**
  * Just like gRPC with proto, thrift generate the Service and methods specify in the IDL
  * Here we Use the Service [[FirstThriftService]] with the access type [[finagle.thrift.rpc.FirstThriftService.MethodPerEndpoint]]
  *
  * Once that we have the service access we can invoke the remote method
  */
object ThriftRPCClient {

  def run(port: Int): Unit = {
    val methodPerEndpoint: FirstThriftService.MethodPerEndpoint =
      Thrift.client.build[FirstThriftService.MethodPerEndpoint](s"localhost:$port")
    val future: Future[String] = methodPerEndpoint.firstRemoteMethod("hello world")
    Await.result(future)
  }

}