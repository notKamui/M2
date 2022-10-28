package com.notkamui.tp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.notkamui.tp2.country.Country
import com.notkamui.tp2.country.CountryDisplayer
import com.notkamui.tp2.country.RankedValueDisplayer
import com.notkamui.tp2.ui.theme.TP2Theme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP2Theme {
                HelloWorld("Android")
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun HelloWorld(name: String) {
    var counter by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            HelloWorldMessage(name, counter)
            CountryDisplayer(Country.Japan)
            WorldMap(mapClick = { counter++ }, mapDoubleClick = { counter += 4 })
        }
    }
}

@Composable
fun HelloWorldMessage(name: String, counter: Int) {
    Text(text = "Hello $name! Counter: $counter", color = Color.Black, fontSize = 32.sp)
}

@Composable
fun WorldMap(mapClick: () -> Unit, mapDoubleClick: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.equirectangular_world_map),
        contentDescription = "World map",
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { mapClick() },
                    onDoubleTap = { mapDoubleClick() }
                )
            }
    )
}

@ExperimentalFoundationApi
@Composable
fun Countries() {
    val countries = listOf(Country.France, Country.Japan, Country.Monaco, Country.Bahamas)
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(countries.size) { index ->
            val country = countries[index]
            Box(Modifier.height(IntrinsicSize.Min)) {
                RankedValueDisplayer(country.area.value, country.area.unit, country.area.rank)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalFoundationApi
fun DefaultPreview() {
    TP2Theme {
        HelloWorld("Kamui")
    }
}