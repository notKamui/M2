package com.notkamui.exam

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.notkamui.exam.components.CountryListDisplay
import com.notkamui.exam.components.FlagGridDisplay
import com.notkamui.exam.model.Country
import com.notkamui.exam.ui.theme.ExamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setContent {
            ExamTheme {
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

private enum class DisplayMode {
    GRID, LIST
}

@Composable
fun App(context: Context) {
    val countries by rememberSaveable { mutableStateOf(Country.loadList(context)) }
    val countriesDisplayed by rememberSaveable { mutableStateOf(countries.shuffled()) }
    var foundCountries by rememberSaveable { mutableStateOf(setOf<Country>()) }
    var displayMode by rememberSaveable { mutableStateOf(DisplayMode.GRID) }
    var selectedCountry by rememberSaveable { mutableStateOf<Country?>(null) }

    val goodCountryText = stringResource(R.string.found_country)
    val wrongCountryText = stringResource(id = R.string.wrong_country)

    when (displayMode) {
        DisplayMode.GRID -> FlagGridDisplay(context, countriesDisplayed, foundCountries) {
            displayMode = DisplayMode.LIST
            selectedCountry = it
        }
        DisplayMode.LIST -> CountryListDisplay(countries, foundCountries) {
            displayMode = DisplayMode.GRID
            if (it == selectedCountry) {
                foundCountries = foundCountries + it
                Toast.makeText(context, goodCountryText, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, wrongCountryText, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
