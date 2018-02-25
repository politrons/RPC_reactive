package finagle.thrift.rpc

import com.twitter.util.Future
import finagle.thrift.FirstThriftService

/**
  * Just like the client, the service extends the service created by the IDL [[FirstThriftService]]
  * using the access type [[finagle.thrift.FirstThriftService.MethodPerEndpoint]]
  *
  * The only thing that the service need to do is implement the remote method create in the service IDL
  */
class FirstThriftServiceImpl extends FirstThriftService.MethodPerEndpoint {

  def firstRemoteMethod(req: String): Future[String] = {
    Future.value(req)
  }

}