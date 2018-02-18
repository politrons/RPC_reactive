package benchmark



import java.nio.charset.Charset

import org.glassfish.grizzly.http.server.{HttpHandler, HttpServer, Request, Response}

object HttpGrizzlyServer {

  def start(port:Int) = {
    val server = HttpServer.createSimpleServer("", "localhost", port)
    server.getServerConfiguration.addHttpHandler(new HttpHandler() {
      @throws[Exception]
      def service(request: Request, response: Response): Unit = {
        val body = request.getPostBody(20).toStringContent(Charset.defaultCharset)
        response.getWriter.write(body)
      }
    }, "/grizzly")
    server.start()
    Thread.sleep(1000)
  }


}
