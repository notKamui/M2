package com.example.tp4.model;

import android.content.Context
import android.graphics.RectF
import java.io.FileInputStream
import java.util.zip.GZIPInputStream
import kotlin.math.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

        fun parseFile(context: Context, path: String): List<Town> =
            context.assets.open(path).bufferedReader().use { it
                .lineSequence()
                .mapNotNull { l -> parseLine(l) }
                .toList() }

        fun parseFileAsync(context: Context, path: String): Flow<TownListLoading> = flow {
            context.assets.open(path).bufferedReader().use {
                val towns = mutableListOf<Town>()
                for (line in it.lineSequence()) {
                    val town = parseLine(line) ?: continue
                    towns += town
                    emit(TownListProgress(towns.size))
                }
                emit(TownListResult(towns))
            }
        }
    }
}

private const val EARTH_RADIUS = 6372800.0

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

fun Collection<Town>.computeRectBounds(): RectF {
    val minLat = minOf { it.latitude }
    val maxLat = maxOf { it.latitude }
    val minLon = minOf { it.longitude }
    val maxLon = maxOf { it.longitude }
    return RectF(minLon, maxLat, maxLon, minLat)
}