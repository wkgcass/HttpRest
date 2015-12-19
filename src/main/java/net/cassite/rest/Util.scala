package net.cassite.rest

import java.util
import java.util.function.{BiConsumer, Consumer}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * util
 */
object Util {
  def listToScala[E](javaList: java.util.List[E]): List[E] = {
    val list = new ListBuffer[E]
    javaList.forEach(new Consumer[E] {
      override def accept(t: E): Unit = t match {
        case m: util.Map[_, _] =>
          list += mapToScala(m.asInstanceOf[util.Map[Any, Any]]).asInstanceOf[E]
        case l: util.List[_] =>
          list += l.asInstanceOf[E]
        case _ => list += t
      }
    })
    list.toList
  }

  def mapToScala[K, V](javaMap: java.util.Map[K, V]): Map[K, V] = {
    val map = new mutable.LinkedHashMap[K, V]
    javaMap.forEach(new BiConsumer[K, V] {
      override def accept(t: K, u: V): Unit = u match {
        case m: util.Map[_, _] =>
          map(t) = mapToScala(m.asInstanceOf[util.Map[K, V]]).asInstanceOf[V]
        case l: util.List[_] =>
          map(t) = listToScala(l.asInstanceOf[util.List[Any]]).asInstanceOf[V]
        case _ => map(t) = u
      }
    })
    map.toMap
  }
}
