package com.notkamui.android.tp1.model

import android.content.Context
import java.io.Serializable
import java.lang.Math.toRadians
import java.util.*
import kotlin.math.*

class City(val name: String, val latitude: Float, val longitude: Float, val population: Int, val elevation: Float, val timeZone: TimeZone): Serializable {

    companion object {
        /** Load a city from a CSV text line */
        fun loadFromLine(line: String): City {
            val c = line.split("\t")
            return City("${c[0]} ${c[3]}", c[1].toFloat(), c[2].toFloat(), c[4].toInt(), c[5].toFloat(), TimeZone.getTimeZone(c[6]))
        }

        /** Load all the cities from a CSV text file */
        fun loadFromAsset(context: Context, path: String): List<City> = context
            .assets
            .open(path)
            .reader()
            .readLines()
            .map { loadFromLine(it) }

        fun loadFromRes(context: Context, resourceId: Int): List<City> = context
            .resources
            .openRawResource(resourceId)
            .reader()
            .readLines()
            .map { loadFromLine(it) }


        const val R = 6372.8 // in kilometers

        /** Compute the distance between two geographical points */
        fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val lambda1 = toRadians(lat1)
            val lambda2 = toRadians(lat2)
            val deltaLambda = toRadians(lat2 - lat1)
            val deltaPhi = toRadians(lon2 - lon1)
            return 2 * R * asin(sqrt(sin(deltaLambda / 2).pow(2.0) + sin(deltaPhi / 2).pow(2.0) * cos(lambda1) * cos(lambda2)))
        }

        fun findNearest(cities: List<City>, latitude: Float, longitude: Float): City? {
            return cities.minByOrNull { haversine(latitude.toDouble(), longitude.toDouble(), it.latitude.toDouble(), it.longitude.toDouble()) }
        }
    }
}
