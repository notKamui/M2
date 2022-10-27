package com.td.either

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ScalaDriverTest extends AnyFlatSpec with Matchers {

  private val wrapper = new ScalaDriver()

  "find" should "be typesafe" in {
    wrapper.find("random") should be(None)

    wrapper.find("typesafe") should be(Some("Ensuring through the type system that no RuntimeException will be thrown"))
  }

  "findMany" should "be typesafe" in {
    wrapper.find() should be(List.empty)

    wrapper.find("nullsafe", "FP", "random1") should be(
      List(
        Some("Prevent using `null` and thus NullPointerException"),
        Some(
          "Functional programming is a style of programming in which solutions are constructed by defining and applying (mathematical) functions"
        ),
        None
      )
    )
  }

  "findAll" should "be typesafe" in {
    wrapper.find(2) should be(
      Right(
        List(
          "A deterministic procedure returns the same output for the same input",
          "A kind describes the structure of a type or type constructor"
        )
      )
    )

    wrapper.find(3) should be(Left("Request too long"))
  }
}

