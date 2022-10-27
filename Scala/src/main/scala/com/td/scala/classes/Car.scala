package com.td.scala.classes

import com.td.scala.constructor.CarConstructor.Color

case class Car(nbDoor: Int, color: Color, nbSeats: Int, price: Double = 0.0, speed: Int = 0) {

  def speedUp(s: Int, turbo: Int => Int = identity[Int]): Either[String, Car] =
    if (s <= 0) {
      Left("Speed must be greater than 0")
    } else {
      Right(copy(speed = turbo(speed + s)))
    }

  def brake(s: Int): Either[String, Car] =
    if (s >= 0) {
      Left("Speed must be lower than 0")
    } else {
      Right(copy(speed = speed - s))
    }

}

