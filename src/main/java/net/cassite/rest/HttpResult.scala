package net.cassite.rest

import java.io.{InputStreamReader, BufferedReader}

import org.apache.http.HttpResponse

/**
 * http connection result
 */
class HttpResult(val http: Http, val response: HttpResponse) {
  val status = response.getStatusLine.getStatusCode
  private val cookieHeaders = response.getHeaders("Set-Cookie")
  private val toAppend = if (cookieHeaders.isEmpty) null else (for (header <- response.getHeaders("Set-Cookie")) yield header.getValue).mkString("").split(";").map(_.split("=")).map(arr => (arr(0), arr(1))).toMap
  val cookies = if (toAppend == null) http.cookies.toMap else http.cookies.toMap ++ toAppend

  private val bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity.getContent))
  private var tmpText = ""
  private var nextLine = bufferedReader.readLine()
  if (nextLine != null) {
    do {
      tmpText = tmpText + nextLine
      nextLine = bufferedReader.readLine()
    } while (nextLine != null)
  }
  bufferedReader.close()

  val text = tmpText.trim

  private var jsonValue: Map[String, Any] = null

  def json = {
    if (jsonValue == null) {
      jsonValue = scala.util.parsing.json.JSON.parseFull(text).get.asInstanceOf[Map[String, Any]]
    }
    jsonValue
  }
}