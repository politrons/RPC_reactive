package finagle.thrift.benchmark

import com.twitter.finagle.Thrift
import com.twitter.util.{Await, Future}
import finagle.BenchmarkUtils
import finagle.BenchmarkUtils._

/**
  * Just like gRPC with proto, thrift generate the Service and methods specify in the IDL
  * Here we Use the Service [[ThriftBenchmarkService]] with the access type
  * [[finagle.thrift.benchmark.ThriftBenchmarkService.MethodPerEndpoint]]
  *
  * Once that we have the service access we can invoke the remote method
  */
object ThriftBenchmarkClient {

  def run(port: Int): Unit = {
    val methodPerEndpoint: ThriftBenchmarkService.MethodPerEndpoint =
      Thrift.client.build[ThriftBenchmarkService.MethodPerEndpoint](s"localhost:$port")
    0 to requestNumber foreach { _ =>
      val future: Future[String] = methodPerEndpoint.connect("value", "value","value","value","value")
      Await.result(future)
    }
  }

}