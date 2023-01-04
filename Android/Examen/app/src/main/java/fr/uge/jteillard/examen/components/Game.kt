package fr.uge.jteillard.examen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.random.Random

private enum class GameState {
    IN_GAME, FINISHED
}

@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun DiceGame() {
    var state by rememberSaveable { mutableStateOf(GameState.IN_GAME) }
    var throws by rememberSaveable { mutableStateOf(0) }

    when (state) {
        GameState.IN_GAME -> GameScreen {
            throws = it
            state = GameState.FINISHED
        }
        GameState.FINISHED -> FinishScreen(throws) { state = GameState.IN_GAME }
    }
}

@Composable
private fun GameScreen(onFinish: (Int) -> Unit) {
    val target by rememberSaveable {
        mutableStateOf(
            Random.nextInt(
                5,
                31
            )
        )
    } // equivalent to 5 throws

    var throwId by rememberSaveable { mutableStateOf(0) }
    var currentValue by rememberSaveable { mutableStateOf(0) }

    LaunchedEffect(currentValue) {
        if (target == currentValue) onFinish(currentValue)
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Target : $target", fontSize = 16.sp)
        Text(text = "Diff : ${currentValue - target}", fontSize = 16.sp)
        Button(onClick = { throwId++ }) {
            Text(text = "Throw")
        }
        MultipleSelectableDice(5, throwId, onThrow = { currentValue = it.sum() })
    }
}

@Composable
private fun FinishScreen(throws: Int, onReplay: () -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Congratulations", fontSize = 16.sp)
        Text(text = "You succeeded with $throws throws", fontSize = 16.sp)
        Button(onClick = onReplay) {
            Text(text = "Play again")
        }
    }
}

