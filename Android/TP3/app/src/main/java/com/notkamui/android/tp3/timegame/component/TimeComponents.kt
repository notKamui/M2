package com.notkamui.android.tp3.timegame.component

import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notkamui.android.tp3.ui.theme.TP3Theme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * [delta] in milliseconds
 */
@Composable
fun DeltaTimeDisplayer(delta: Long, prefix: String = "", suffix: String = "", size: TextUnit = 32.sp) {
    val (minutes, seconds, cents) = decomposeMillis(delta)
    Text(text = "$prefix${minutes.pad()}:${seconds.pad()}.${cents.pad()}$suffix", fontSize = size)
}

@Composable
fun Chronometer(startTime: Long, endTime: Long? = null, onUpdate: (Long) -> Unit = {}) {
    val delta = if (endTime == null) {
        var currentTime by remember { mutableStateOf(SystemClock.elapsedRealtime()) }
        LaunchedEffect(Unit) {
            while (isActive) {
                currentTime = SystemClock.elapsedRealtime()
                delay(10)
            }
        }
        currentTime - startTime
    } else {
        endTime - startTime
    }
    onUpdate(delta)
    DeltaTimeDisplayer(delta)
}

@Composable
fun ChronometerManager() {
    var isRunning by remember { mutableStateOf(false) }
    var startTime by remember { mutableStateOf(0L) }
    var endTime by remember { mutableStateOf<Long?>(0L) }
    Column(
        Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Chronometer(startTime, endTime)

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
            }, enabled = isRunning) {
                Text(text = "Stop")
            }
        }
    }
}

private fun decomposeMillis(millis: Long): Triple<Long, Long, Long> {
    val minutes = millis / 1000 / 60
    val seconds = (millis / 1000) % 60
    val centiseconds = (millis % 1000) / 10
    return Triple(minutes, seconds, centiseconds)
}

private fun Long.pad(length: Int = 2) = toString().padStart(length, '0')

@Composable
@Preview(showBackground = true)
private fun Preview() {
    TP3Theme {
        ChronometerManager()
    }
}