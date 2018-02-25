package finagle.thrift.benchmark

import com.twitter.finagle.Thrift

/**
  * In order to use Thrift service we need to use a Thrift server.
  * Here we add the service just like we do in regular finagle http server.
  */
object ThriftBenchmarkServer {

  def start(port:Int): Unit = {
    Thrift.server.serveIface(s"localhost:$port", new ThriftBenchmarkServiceImpl)
  }
}