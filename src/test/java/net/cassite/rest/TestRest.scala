package net.cassite.rest

import org.scalatest._

/**
 * test rest
 */
class TestRest extends FlatSpec with Matchers {
  "ip 8.8.8.8 info" should "have code 0 and country 美国" in {
    GET(new Http / "ip.taobao.com" * 80 / "service" / "getIpInfo.php" & "ip" === "8.8.8.8") {
      result =>
        result.status should be(200)
        result.json("code") should be(0)
        result.json("data").asInstanceOf[Map[String,Object]]("country") should be("美国")
    }
  }

  "localhost:3000" should """be {text:"TEXT",number:1,float:2.3}""" in {
    GET(new Http / localhost * 3000) {
      result =>
        result.status should be(200)
        result.json should be(Map("text" -> "TEXT", "number" -> 1, "float" -> 2.3))
    }
  }
}