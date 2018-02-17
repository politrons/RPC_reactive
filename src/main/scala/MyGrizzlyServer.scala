

import org.glassfish.grizzly.http.server.{HttpHandler, HttpServer, Request, Response}
import java.nio.charset.Charset

object MyGrizzlyServer {

  def start = {
    val server = HttpServer.createSimpleServer("", "localhost", 1982)
    server.getServerConfiguration.addHttpHandler(new HttpHandler() {
      @throws[Exception]
      def service(request: Request, response: Response): Unit = {
        val body = request.getPostBody(999999).toStringContent(Charset.defaultCharset)
        response.getWriter.write(body)
      }
    }, "/grizzly")
    server.start()
    Thread.sleep(1000)
  }


}
