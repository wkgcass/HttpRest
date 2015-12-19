package net.cassite.rest

/**
 * test grammar
 */
object TestGrammar extends App {

  GET(new Http / "localhost" * 8080 / "user" / 1 & "a" === 1 & "b" === 2) { res =>

  }


}
