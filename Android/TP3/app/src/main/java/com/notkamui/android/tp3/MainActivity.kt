package com.notkamui.android.tp3

import android.os.Bundle
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.notkamui.android.tp3.timegame.component.Chronometer
import com.notkamui.android.tp3.timegame.component.ChronometerManager
import com.notkamui.android.tp3.timegame.game.ChronoGame
import com.notkamui.android.tp3.timegame.game.GameManager
import com.notkamui.android.tp3.ui.theme.TP3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GameManager()
                }
            }
        }
    }
}
