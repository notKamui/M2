package com.example.tp4.model;

import android.content.Context
import android.graphics.RectF
import java.io.FileInputStream
import java.util.zip.GZIPInputStream
import kotlin.math.*

data class Town(val name: String, val latitude: Float, val longitude: Float, val zipcode: String) {
    companion object {
        fun parseLine(line: String): Town? {
            val components = line.split(";")
            val name = components.getOrNull(1)
            val zipcode = components.getOrNull(2)
            val coordinates = components.getOrNull(5)
                ?.let { it1 -> it1.split(",").mapNotNull { it.toFloatOrNull() } }
            return if (name != null && zipcode != null && zipcode < "96000" && !zipcode.startsWith("20") && coordinates?.size == 2) Town(
                name,
                coordinates[0],
                coordinates[1],
                zipcode
            )
            else null
        }

        fun parseFile(context: Context, path: String) =
            context.assets.open(path).bufferedReader()
                .use { it.lineSequence().mapNotNull { l -> parseLine(l) }.toList() }
    }
}

val MIN_DISTANCE = 100000.0
val EARTH_RADIUS = 6372800.0

/**
 * Haversine formula. Giving great-circle distances between two points on a sphere from their longitudes and latitudes.
 * It is a special case of a more general formula in spherical trigonometry, the law of haversines, relating the
 * sides and angles of spherical "triangles".
 *
 * https://rosettacode.org/wiki/Haversine_formula#Java
 *
 * @return Distance in kilometers
 */
fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val originLat = Math.toRadians(lat1)
    val destinationLat = Math.toRadians(lat2)

    val a = sin(dLat / 2).pow(2.0) + sin(dLon / 2).pow(2.0) * cos(originLat) * cos(destinationLat)
    val c = 2 * asin(sqrt(a))
    return EARTH_RADIUS * c;
}

fun MutableList<Town>.addRandomTown(source: List<Town>) {
    fun isValidTown(town: Town) = this.all {
        it != town && haversine(
            it.latitude.toDouble(),
            it.longitude.toDouble(),
            town.latitude.toDouble(),
            town.longitude.toDouble()
        ) > MIN_DISTANCE
    }

    var town = source.random()
    while (!isValidTown(town)) town = source.random()
    add(town)
}

fun Collection<Town>.computeRectBounds(): RectF {
    val minLat = this.minOf { it.latitude }
    val maxLat = this.maxOf { it.latitude }
    val minLon = this.minOf { it.longitude }
    val maxLon = this.maxOf { it.longitude }
    return RectF(minLon, maxLat, maxLon, minLat)
}