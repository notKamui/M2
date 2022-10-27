package com.td.scala

import com.td.scala.classes.Car
import com.td.scala.constructor.CarConstructor
import com.td.scala.functions.CarUtilities
import com.td.scala.functions.CarUtilities.{showRoomFormat, technicalFormat}

object Main {

  def main(args: Array[String]): Unit = {
    Car(4, CarConstructor.Red, 5)
      .speedUp(10)
      .fold(
        err => println(err),
        car => {
          println(car.speed)
          car.brake(7)
            .fold(
              err => println(err),
              car => println(car.speed)
            )
        }
      )

    (1 to 7).foreach(i => println(CarConstructor.searchCarById(i).fold("Not found")(_.color.toString)))
    CarConstructor.searchCarsByColor(CarConstructor.Blue).foreach(car => println(car.nbSeats))

    println("Prepare characteristic sheets (technical) for all green car")
    CarConstructor.searchCarsByColor(CarConstructor.Green).foreach(car => println(technicalFormat(car)))

    println("Prepare characteristic sheets (showroom) for all pink car")
    CarConstructor.searchCarsByColor(CarConstructor.Pink).foreach(car => println(showRoomFormat(car)))

    println(
      """Build a concept car that embeds a turbo that make it the faster car in the world
        |when the pilot speed up, its speed rise up by 10 times than standard car
        |""".stripMargin)
    val flash = Car(4, CarConstructor.Red, 5).speedUp(10, CarUtilities.maxiTurbo)
    flash.fold(
      err => println(err),
      car => println(showRoomFormat(car))
    )

    println(
      """Build a standard car that embeds a turbo that make it the luxurious car in the world
        |when the driver speed up, its speed rise up by 2 times
        |""".stripMargin)
    val luxury = Car(4, CarConstructor.Red, 5).speedUp(10, CarUtilities.softTurbo)
    luxury.fold(
      err => println(err),
      car => println(showRoomFormat(car))
    )

  }
}
