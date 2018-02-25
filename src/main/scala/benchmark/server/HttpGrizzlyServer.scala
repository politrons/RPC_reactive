package benchmark.server

import java.nio.charset.Charset

import com.google.gson.Gson
import com.politrons.grpc.Pojo
import org.glassfish.grizzly.http.server.{HttpHandler, HttpServer, Request, Response}

object HttpGrizzlyServer {

  def start(port: Int) = {
    val server = HttpServer.createSimpleServer("", "localhost", port)
    server.getServerConfiguration.addHttpHandler(new HttpHandler() {
      @throws[Exception]
      def service(request: Request, response: Response): Unit = {
        val body = request.getPostBody(100).toStringContent(Charset.defaultCharset)
        val gson = new Gson()
        val pojo = gson.fromJson(body, classOf[Pojo])
        response.getWriter.write(gson.toJson(pojo))
      }
    }, "/grizzly")
    server.start()
    Thread.sleep(1000)
  }


}
