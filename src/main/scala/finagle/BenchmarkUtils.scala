package finagle

object BenchmarkUtils {

  var requestNumber = 1000

  def getPayload: String = {
   """
     |{
     |  "arg":"value",
     |  "arg1":"value",
     |  "arg2":"value",
     |  "arg3":"value",
     |  "arg4":"value"
     |}
   """.stripMargin
  }
}
