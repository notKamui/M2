package com.example.tp4

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp4.component.TownManager
import com.example.tp4.model.Town
import com.example.tp4.model.TownListProgress
import com.example.tp4.model.TownListResult
import com.example.tp4.ui.theme.TP4Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setContent {
            TP4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(context)
                }
            }
        }
    }
}

@Composable
fun App(context: Context) {
    var loading by remember { mutableStateOf(true) }
    var amountLoaded by remember { mutableStateOf(0) }
    val towns = remember { mutableStateListOf<Town>() }

    LaunchedEffect(Unit) {
        Town.parseFileAsync(context, "laposte_hexasmal.csv")
            .flowOn(Dispatchers.Default)
            .conflate()
            .collect { when (it) {
                is TownListProgress -> {
                    amountLoaded = it.townNumber
                    Log.d("MainActivity", "Loaded $amountLoaded towns")
                }
                is TownListResult -> {
                    towns.addAll(it.townList)
                    loading = false
                }
            }}
    }

    Column(Modifier.fillMaxSize()) {
        if (loading) {
            LoadingDisplay(amountLoaded)
        } else {
            TownManager(towns)
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun LoadingDisplay(amountLoaded: Int) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(Modifier) {
        val text = textMeasurer.measure(AnnotatedString("Loading... Loaded $amountLoaded towns"))
        drawText(text, topLeft = Offset(0f, 0f))
    }
}
