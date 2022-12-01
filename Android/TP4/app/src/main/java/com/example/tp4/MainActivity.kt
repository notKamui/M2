package com.example.tp4

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp4.component.TownDisplayer
import com.example.tp4.model.Town
import com.example.tp4.model.addRandomTown
import com.example.tp4.ui.theme.TP4Theme

class MainActivity : ComponentActivity() {

    private lateinit var towns: List<Town>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        towns = Town.parseFile(this, "laposte_hexasmal.csv")

        val randomTowns = buildList {
            repeat(30) { addRandomTown(towns) }
        }.toSet()
        setContent {
            TP4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxSize()) {
                        TownDisplayer(
                            setOf(towns.find { it.name.contains("PARIS") }!!),
                            ImageBitmap.imageResource(id = R.drawable.france_map)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TP4Theme {
        Greeting("Android")
    }
}