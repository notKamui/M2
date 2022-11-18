package com.notkamui.tp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notkamui.tp2.country.Country
import com.notkamui.tp2.country.CountryComponent
import com.notkamui.tp2.country.CountryDisplayer
import com.notkamui.tp2.country.FlagsComponent
import com.notkamui.tp2.country.FlagsDisplayer
import com.notkamui.tp2.country.MainComponent
import com.notkamui.tp2.country.RankedValueDisplayer
import com.notkamui.tp2.ui.theme.TP2Theme

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TP2Theme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    var mainComponent by remember { mutableStateOf<MainComponent>(FlagsComponent) }

    Scaffold(
        topBar = {
            TopAppBar {
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text(text = "Country Facts", fontSize = 32.sp)
                    Button(onClick = { mainComponent = FlagsComponent }) {
                        Text(text = "Change country")
                    }
                }
            }
        },
        drawerContent = {
            FlagsDisplayer(countries = Country.all()) { country ->
                mainComponent = CountryComponent(country)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mainComponent = CountryComponent(Country.all().random())
            }) {
                Text(text = "Random country")
            }
        },
        bottomBar = {
            BottomAppBar {}
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            when (val component = mainComponent) {
                is FlagsComponent -> FlagsDisplayer(Country.all()) {
                    mainComponent = CountryComponent(it)
                }
                is CountryComponent -> CountryDisplayer(component.country)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalFoundationApi
fun DefaultPreview() {
    TP2Theme {
        App()
    }
}