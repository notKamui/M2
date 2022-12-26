package com.example.tp4.util

import com.example.tp4.model.Town
import com.example.tp4.model.haversine

private const val MIN_DISTANCE = 100.0 * 1000.0

fun MutableList<Town>.addRandomTown(source: List<Town>) {
    while (true) {
        val town = source.random()
        if (contains(town)) continue
        if (any { haversine(
                it.latitude.toDouble(),
                it.longitude.toDouble(),
                town.latitude.toDouble(),
                town.longitude.toDouble()
            ) < MIN_DISTANCE
        }) continue

        add(town)
        return
    }
}

val List<Town>.pathLength: Double get() {
    if (isEmpty()) return 0.0

    val closedPath: List<Town> = this + first()
    return closedPath.zipWithNext().sumOf { (a, b) ->
        haversine(
            a.latitude.toDouble(),
            a.longitude.toDouble(),
            b.latitude.toDouble(),
            b.longitude.toDouble()
        )
    }
}