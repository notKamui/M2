package com.example.tp4.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.example.tp4.R
import com.example.tp4.model.SimulatedAnnealingParams
import com.example.tp4.model.Town
import com.example.tp4.model.computeWithSimulatedAnnealing
import com.example.tp4.util.addRandomTown
import com.example.tp4.util.pathLength
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

private const val MAX_TOWNS = 25

@Composable
fun TownManager(towns: List<Town>) {
    var townCountToDisplay by remember { mutableStateOf(10f) }
    val townsToDisplay = remember { mutableStateListOf<Town>() }

    val path = remember { mutableStateListOf<Town>() }
    var temperature by remember { mutableStateOf(.5f) }

    LaunchedEffect(townCountToDisplay) {
        if (townCountToDisplay > townsToDisplay.size) {
            val toAdd = (townCountToDisplay - townsToDisplay.size).toInt()
            repeat(toAdd) { townsToDisplay.addRandomTown(towns) }
        } else if (townCountToDisplay < townsToDisplay.size) {
            val toRemove = (townsToDisplay.size - townCountToDisplay).toInt()
            repeat(toRemove) { townsToDisplay.removeLast() }
        }
    }

    LaunchedEffect(townCountToDisplay, temperature) {
        computeWithSimulatedAnnealing(
            townsToDisplay,
            1_000_000,
            SimulatedAnnealingParams(temperatureDecreaseRatio = temperature.toDouble())
        )
            .flowOn(Dispatchers.Default)
            .conflate()
            .onEach { delay(1000) }
            .collect {
                path.clear()
                path.addAll(it)
            }
    }

    Column(Modifier.fillMaxSize()) {
        TownDisplayer(
            townsToDisplay,
            path,
            "Path length: ${path.pathLength.toInt() / 1000} km",
            ImageBitmap.imageResource(id = R.drawable.france_map)
        )

        Text(text = "Number of towns:")
        Slider(
            value = townCountToDisplay,
            onValueChange = {
                townCountToDisplay = it
                path.clear()
            },
            valueRange = 0f..MAX_TOWNS.toFloat(),
            steps = MAX_TOWNS + 1
        )

        Text(text = "Path temperature decrease ratio:")
        Slider(
            value = temperature,
            onValueChange = { temperature = it },
            valueRange = 0f..1f,
        )
    }
}