package com.td.either

import scala.util.{ Failure, Success, Try }

/** Typesafe wrapper for Scala of Java's JavaDriver library
  */
class ScalaDriver() {
  // Provide .asScala on java List
  import collection.JavaConverters._

  private val library = new JavaDriver();

  def find(key: String): Option[String] = Option(library.findOne(key))


  def find(keys: String*): List[Option[String]] = library.findMany(keys.asJava).asScala.toList.map(Option(_))


  def find(max: Int): Either[String, List[String]] = try {
    Right(library.findAll(max).asScala.toList)
  } catch {
    case e: RequestSizeException => Left(e.getMessage)
  }
}

