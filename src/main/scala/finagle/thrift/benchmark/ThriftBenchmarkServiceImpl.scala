package finagle.thrift.benchmark

import com.twitter.util.Future

/**
  * Just like the client, the service extends the service created by the IDL [[ThriftBenchmarkService]]
  * using the access type [[finagle.thrift.benchmark.ThriftBenchmarkService.MethodPerEndpoint]]
  *
  * The only thing that the service need to do is implement the remote method create in the service IDL
  */
class ThriftBenchmarkServiceImpl extends ThriftBenchmarkService.MethodPerEndpoint {

  def connect(req1: String,
              req2: String,
              req3: String,
              req4: String,
              req5: String): Future[String] = {
    Future.value(req1)
  }

}