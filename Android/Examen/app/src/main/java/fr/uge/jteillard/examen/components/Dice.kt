package fr.uge.jteillard.examen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import fr.uge.jteillard.examen.util.MatrixDiceHelper
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SimpleDice(value: Int, color: Color) {
    Box(
        modifier = Modifier
            .border(width = 10.dp, color = Color.Black)
            .aspectRatio(1f / 1f)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        if (value != 0) {
            Text(text = "$value", fontSize = 30.em)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatrixDice(value: Int, color: Color) {
    Box(
        modifier = Modifier
            .border(width = 10.dp, color = Color.Black)
            .aspectRatio(1f / 1f)
            .background(color),
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            items(9) {
                val checkedColor = if (MatrixDiceHelper.getMark(value, it)) {
                    Color.Black
                } else {
                    color
                }
                Box(
                    Modifier
                        .fillMaxSize()
                        .border(2.dp, color = color)
                        .background(checkedColor)
                        .aspectRatio(1f / 1f)
                )
            }
        }
    }
}

@Composable
fun ThrowableDice(throwId: Int, onThrow: (Int) -> Unit = {}) {
    var currentValue by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(throwId) {
        if (throwId == 0) {
            currentValue = 0
            return@LaunchedEffect
        }

        currentValue = Random.nextInt(1, 7)
        onThrow(currentValue)
    }

    MatrixDice(currentValue, Color.Red)
}

@Composable
fun SuspenseDice(
    throwId: Int,
    color: Color,
    throwDuration: Long = 2000L,
    onThrow: (Int) -> Unit = {},
) {
    var currentValue by rememberSaveable { mutableStateOf(0) }
    var tempValue by rememberSaveable { mutableStateOf(0) }

    var suspending by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(throwId) {
        if (throwId == 0) {
            currentValue = 0
            return@LaunchedEffect
        }

        suspending = true
    }

    // wait total duration before stopping
    LaunchedEffect(suspending) {
        if (!suspending) return@LaunchedEffect
        delay(throwDuration)
        suspending = false
        currentValue = tempValue
        onThrow(currentValue)
    }

    // repeat value change
    LaunchedEffect(suspending) {
        if (!suspending) return@LaunchedEffect

        while (true) {
            tempValue = Random.nextInt(1, 7)
            delay(50L)
        }
    }

    MatrixDice(tempValue, color)
}

@Composable
fun MultipleDice(
    diceNumber: Int,
    throwId: Int,
    throwDuration: Long = 2000L,
    throwDelay: Long = 500L,
    onThrow: (List<Int>) -> Unit = {},
) {
    // throw id to value, by dice id
    val dices = remember { mutableStateListOf(*Array(diceNumber) { 0 to 0 }) }

    LaunchedEffect(throwId) {
        if (throwId == 0) {
            repeat(diceNumber) { dices[it] = 0 to 0 }
            return@LaunchedEffect
        }

        for (diceId in dices.indices) {
            val (diceThrowId, value) = dices[diceId]
            dices[diceId] = diceThrowId + 1 to value
            delay(throwDelay)
        }

        // wait for the last one to finish
        delay(throwDuration)

        onThrow(dices.map { it.second })
    }

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(diceNumber) { diceId ->
            Box(
                Modifier
                    .width(IntrinsicSize.Max)
                    .weight(1f / diceNumber)
            ) {
                val (diceThrowId, value) = dices[diceId]
                SuspenseDice(diceThrowId, Color.Red, throwDuration) {
                    dices[diceId] = diceThrowId to it
                }
            }
        }
    }
}

@Composable
fun MultipleSelectableDice(
    diceNumber: Int,
    throwId: Int,
    throwDuration: Long = 2000L,
    throwDelay: Long = 500L,
    onThrow: (List<Int>) -> Unit = {},
    onSelectionChange: (Set<Int>) -> Unit = {}
) {
    // throw id to value to selection state, by dice id
    val dices = remember { mutableStateListOf(*Array(diceNumber) { Triple(0, 0, true) }) }

    LaunchedEffect(throwId) {
        if (throwId == 0) {
            repeat(diceNumber) { dices[it] = Triple(0, 0, true) }
            return@LaunchedEffect
        }

        for (diceId in dices.indices) {
            val (diceThrowId, value, selected) = dices[diceId]
            if (!selected) continue // ignore dice if not selected
            dices[diceId] = Triple(diceThrowId + 1, value, true)
            delay(throwDelay)
        }

        // wait for the last one to finish
        delay(throwDuration)

        onThrow(dices.map { it.second })
    }

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(diceNumber) { diceId ->
            val (diceThrowId, value, selected) = dices[diceId]
            Box(
                Modifier
                    .width(IntrinsicSize.Max)
                    .weight(1f / diceNumber)
                    .clickable {
                        dices[diceId] = Triple(diceThrowId, value, !selected)
                        onSelectionChange(
                            dices
                                .mapIndexedNotNull { index, (_, _, selected) -> if (selected) index else null }
                                .toSet()
                        )
                    },
            ) {
                val color = if (selected) {
                    Color.Blue
                } else {
                    Color.Red
                }
                SuspenseDice(diceThrowId, color, throwDuration) {
                    dices[diceId] = Triple(diceThrowId, it, selected)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
private fun SimpleDiceTester() {
    var value by rememberSaveable { mutableStateOf(0f) }

    Column(Modifier.fillMaxSize()) {
        Slider(
            value = value,
            onValueChange = { value = it },
            steps = 5,
            valueRange = 0f..6f,
        )

        // SimpleDice(value = value.toInt(), color = Color.Red)
        MatrixDice(value = value.toInt(), color = Color.Red)
    }
}

//@Preview(showBackground = true)
@Composable
private fun DiceAccumulator() {
    var sum by rememberSaveable { mutableStateOf(0) }
    var throwId by rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total : $sum")
        Button(onClick = { throwId++ }) {
            Text(text = "Launch")
        }
        //ThrowableDice(throwId) { sum += it }
        SuspenseDice(throwId, Color.Red) { sum += it }
    }
}

//@Preview(showBackground = true)
@Composable
private fun MultipleDiceAccumulator() {
    var sum by rememberSaveable { mutableStateOf(0) }
    var throwId by rememberSaveable { mutableStateOf(0) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total : $sum")
        Button(onClick = { throwId++ }) {
            Text(text = "Launch")
        }
        MultipleDice(3, throwId) { sum += it.sum() }
    }
}

@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
private fun MultipleSelectableDiceAccumulator() {
    val diceNumber = 5

    var sum by rememberSaveable { mutableStateOf(0) }
    var throwId by rememberSaveable { mutableStateOf(0) }
    var selectedDices by rememberSaveable { mutableStateOf(List(diceNumber) { it }) }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total : $sum")
        Text(text = "Selected dices : $selectedDices")
        Button(onClick = { throwId++ }) {
            Text(text = "Launch")
        }
        MultipleSelectableDice(diceNumber, throwId, onThrow = { sum += it.sum() }) {
            selectedDices = it.toList()
        }
    }
}