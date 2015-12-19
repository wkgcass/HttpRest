package net.cassite

import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{HttpPut, HttpDelete, HttpPost, HttpGet}
import org.apache.http.impl.client.HttpClients
import org.apache.http.message.BasicNameValuePair
import scala.collection.JavaConversions._

/**
 * common methods and implicit conversions
 */
package object rest {
  val localhost = "127.0.0.1"

  implicit def strToURLParam(str: String): URLString = new URLString(str)

  def GET[R](http: Http)(result: HttpResult => R): R = {
    val client = HttpClients.createDefault()
    val get = new HttpGet(http.toString)
    val response = client.execute(get)
    val r = result(new HttpResult(http, response))
    response.close()
    client.close()
    r
  }

  def POST[R](http: Http)(result: HttpResult => R): R = {
    val client = HttpClients.createDefault()
    val post = new HttpPost(http.url)

    val paramList = for (entry <- http.param) yield new BasicNameValuePair(entry._1, entry._2)
    val uefEntity = new UrlEncodedFormEntity(paramList.toList, "UTF-8")
    post.setEntity(uefEntity)

    val response = client.execute(post)
    val r = result(new HttpResult(http, response))
    response.close()
    client.close()
    r
  }

  def DELETE[R](http: Http)(result: HttpResult => R): R = {
    val client = HttpClients.createDefault()
    val delete = new HttpDelete(http.toString)

    val response = client.execute(delete)
    val r = result(new HttpResult(http, response))
    response.close()
    client.close()
    r
  }

  def PUT[R](http: Http)(result: HttpResult => R): R = {
    val client = HttpClients.createDefault()
    val put = new HttpPut(http.url)

    val paramList = for (entry <- http.param) yield new BasicNameValuePair(entry._1, entry._2)
    val uefEntity = new UrlEncodedFormEntity(paramList.toList, "UTF-8")
    put.setEntity(uefEntity)

    val response = client.execute(put)
    val r = result(new HttpResult(http, response))
    response.close()
    client.close()
    r
  }
}
