package net.cassite.rest

import java.net.URLEncoder

import scala.collection.mutable

/**
 * retrieve restful api through http
 */
class Http {
  protected var protocol = "http"
  protected var host: String = null
  protected var port = 80

  protected var path = ""

  val param = new mutable.LinkedHashMap[String, String]
  val cookies = new mutable.LinkedHashMap[String, String]

  def /(path: String) = {
    if (host == null)
      host = path
    else
      this.path += ("/" + path)
    this
  }

  def /(path: Any) = {
    this.path += ("/" + path.toString)
    this
  }

  def *(port: Int) = {
    this.port = port
    this
  }

  def &(tuple2: (String, String)) = {
    param(tuple2._1) = tuple2._2
    this
  }

  def url = {
    val sb = new StringBuilder
    sb ++= protocol + "://"
    sb ++= host + ":" + port + path
    sb.toString()
  }

  def cookie(key: String, value: Any) = {
    cookies(key) = value.toString
    this
  }

  override def toString = {
    val sb = new StringBuilder
    sb ++= url
    if (param.nonEmpty) {
      sb ++= "?"
      var isFirst = true
      param.foreach { entry =>
        if (isFirst) {
          isFirst = false
        } else {
          sb ++= "&"
        }
        sb ++= entry._1 ++= "=" ++= URLEncoder.encode(entry._2, "UTF-8")
      }
    }
    sb.toString()
  }
}