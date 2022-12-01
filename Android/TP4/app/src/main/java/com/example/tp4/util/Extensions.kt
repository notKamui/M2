package com.example.tp4.util

import com.example.tp4.model.Town
import com.example.tp4.model.haversine
import java.security.SecureRandom
import kotlin.random.Random

private val rng = Random(System.currentTimeMillis())

fun MutableList<Town>.addRandomTown(source: List<Town>) {
    while (true) {
        val town = source[rng.nextInt(source.size)]
        if (contains(town)) continue
        if (any { haversine(
                it.latitude.toDouble(),
                it.longitude.toDouble(),
                town.latitude.toDouble(),
                town.longitude.toDouble()
            ) < 100.0 * 1000.0
        }) continue

        add(town)
        return
    }
}