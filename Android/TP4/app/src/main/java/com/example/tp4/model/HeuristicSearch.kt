package com.example.tp4.model

import com.example.tp4.util.pathLength
import java.lang.Math.random
import kotlin.math.exp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private fun <T> MutableList<T>.swap(a: Int, b: Int) {
    val tmp = this[a]
    this[a] = this[b]
    this[b] = tmp
}

data class SimulatedAnnealingParams(
    val startTemperature: Double = 1.0,
    val temperatureDecreaseRatio: Double = 0.5,
)

fun computeWithSimulatedAnnealing(
    initialPath: List<Town>,
    iterations: Long,
    params: SimulatedAnnealingParams = SimulatedAnnealingParams()
): Flow<List<Town>> = flow {
    val currentPath = mutableListOf<Town>().apply { addAll(initialPath) }
    var currentDistance = currentPath.pathLength
    var currentTemperature = params.startTemperature

    fun acceptChange(deltaDistance: Double): Boolean {
        if (deltaDistance < 0) return true
        val v = exp(-deltaDistance / currentDistance / currentTemperature)
        return random() < v
    }

    for (i in 0 until iterations) {
        // pick two random points in the circuit and swap them
        val a = currentPath.indices.random()
        val b = currentPath.indices.random()
        if (a != b) {
            currentPath.swap(a, b)
            val newDistance = currentPath.pathLength // could be optimized
            if (acceptChange(newDistance - currentDistance)) {
                currentDistance = newDistance
            } else {
                // we cancel the swap
                currentPath.swap(a, b)
            }
            // we decrement the temperature
            currentTemperature *= (1.0 - params.temperatureDecreaseRatio)
        }
        emit(currentPath)
    }

    emit(currentPath)
}