package net.cassite.rest

/**
 * string that supports '===' used in url parameters
 */
class URLString(val str: String) {
  def ===(any: Any) = (str, any.toString)
}
