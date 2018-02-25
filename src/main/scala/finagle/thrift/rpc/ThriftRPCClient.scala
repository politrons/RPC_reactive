package finagle.thrift.rpc

import com.politrons.grpc.benchmark.BenchmarkUtils
import com.politrons.grpc.benchmark.BenchmarkUtils._
import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import finagle.thrift.FirstThriftService

/**
  * Just like gRPC with proto, thrift generate the Service and methods specify in the IDL
  * Here we Use the Service [[FirstThriftService]] with the access type [[finagle.thrift.FirstThriftService.MethodPerEndpoint]]
  *
  * Once that we have the service access we can invoke the remote method
  */
object ThriftRPCClient {

  def run(port:Int): Unit ={
    val methodPerEndpoint: FirstThriftService.MethodPerEndpoint =
      Thrift.client.build[FirstThriftService.MethodPerEndpoint](s"localhost:$port")
    0 to requestNumber foreach { index =>
      val future: Future[String] = methodPerEndpoint.firstRemoteMethod("hello world")
      Await.result(future)
    }
  }

}