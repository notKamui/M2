package com.notkamui.android.tp3.timegame.game

import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notkamui.android.tp3.timegame.component.Chronometer
import com.notkamui.android.tp3.timegame.component.DeltaTimeDisplayer
import com.notkamui.android.tp3.timegame.game.GameState.STARTING
import com.notkamui.android.tp3.timegame.game.GameState.STOPPED
import kotlin.math.abs

@Composable
fun ChronoGame(expectedTime: Long, onVerdict: (Long) -> Unit) {
    var isRunning by remember { mutableStateOf(false) }
    var hidden by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }
    var endTime by remember { mutableStateOf<Long?>(0L) }
    Column(
        Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        ) {
            Chronometer(startTime, endTime) { elapsed ->
                if (isRunning && elapsed > expectedTime / 2) {
                    hidden = true
                }
            }
            if (hidden) Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Button(onClick = {
                startTime = SystemClock.elapsedRealtime()
                endTime = null
                isRunning = true
            }, enabled = !isRunning) {
                Text(text = "Start")
            }
            Button(onClick = {
                endTime = SystemClock.elapsedRealtime()
                isRunning = false
                hidden = false
                onVerdict(endTime!! - startTime)
            }, enabled = isRunning) {
                Text(text = "Stop")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GameManager() {
    var gameState by remember { mutableStateOf(STARTING) }
    var expectedTime by remember { mutableStateOf(10L) }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(10.dp)
    ) {
        if (gameState == STARTING) {
            Text(text = "Game expected time: $expectedTime", fontSize = 32.sp)
            Slider(
                value = expectedTime.toFloat(),
                valueRange = 0f..60f,
                onValueChange = { expectedTime = it.toLong() })
            Button(onClick = { gameState = GameState.RUNNING }) {
                Text(text = "Begin")
            }
        } else {
            var verdict by remember { mutableStateOf<Long?>(null) }
            val expectedTimeAsMillis = expectedTime * 1000
            ChronoGame(expectedTimeAsMillis) { elapsed ->
                gameState = STOPPED
                verdict = elapsed
            }

            if (gameState == STOPPED) {
                Text(text = "Game ended !", fontSize = 32.sp)
                val difference = verdict!! - expectedTimeAsMillis
                val sign = if (difference < 0) "-" else "+"
                val percent = (abs(difference).toFloat() / expectedTimeAsMillis.toFloat()) * 100f
                DeltaTimeDisplayer(
                    abs(difference),
                    prefix = "Time difference: $sign",
                    suffix = " ($sign${"%.2f".format(percent)}%)",
                    size = 16.sp
                )
                Button(onClick = { gameState = STARTING }) {
                    Text(text = "Restart")
                }
            }
        }
    }
}

private enum class GameState {
    STARTING, RUNNING, STOPPED
}
