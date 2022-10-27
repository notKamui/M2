package com.td.scala.functions

import com.td.scala.classes.Car

object CarUtilities {

  /** Price mustn't appear, only color, nbDoor, nbPlaces* */
  val technicalFormat: Car => String = car => s"Car: color:${car.color} doors:${car.nbDoor} seats:${car.nbSeats}"

  /** All caracteristics must me displayed* */
  val showRoomFormat: Car => String = car => s"Car: colors:${car.color} doors:${car.nbDoor} seats:${car.nbSeats} price:${car.price} speed:${car.speed}"

  /** Multiply parameter by 10* */
  val maxiTurbo: Int => Int = _ * 10

  /** Multiply parameter by 2* */
  val softTurbo: Int => Int = _ * 2

}
