package benchmark.server

import java.nio.charset.Charset

import com.google.gson.Gson
import com.politrons.avro.{DeserializeAvro, SerializeAvro}
import com.politrons.grpc.Pojo
import org.glassfish.grizzly.http.server.{HttpHandler, HttpServer, Request, Response}

object HttpAvroServer {

  def start(port: Int) = {
    val server = HttpServer.createSimpleServer("", "localhost", port)
    server.getServerConfiguration.addHttpHandler(new HttpHandler() {
      @throws[Exception]
      def service(request: Request, response: Response): Unit = {
        val body: String = request.getPostBody(100).toStringContent(Charset.defaultCharset)
        DeserializeAvro.fromByteArray(body.getBytes())
        response.getWriter.write(new String(SerializeAvro.toByteArray))
      }
    }, "/avro")
    server.start()
    Thread.sleep(1000)
  }


}
