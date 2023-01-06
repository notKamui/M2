package com.notkamui.exam.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.notkamui.exam.model.Country

@Composable
fun CountryListDisplay(countries: List<Country>, foundCountries: Set<Country>, onCountryClick: (Country) -> Unit) {
    var search by rememberSaveable { mutableStateOf("") }

    Scaffold(Modifier
        .fillMaxSize(),
        topBar = { TextField(modifier = Modifier.fillMaxWidth(), value = search, onValueChange = { search = it }) }
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            items(countries.filter { search.lowercase() in it.name.lowercase() }) { country ->
                CountryDisplay(country, country in foundCountries, onCountryClick)
            }
        }
    }
}

@Composable
fun CountryDisplay(country: Country, found: Boolean, onCountryClick: (Country) -> Unit) {
    val modifier = if (found) Modifier else Modifier.clickable { onCountryClick(country) }
    Text(modifier = modifier,
        text = country.name,
        style = TextStyle(textDecoration = if (found) TextDecoration.LineThrough else TextDecoration.None)
    )
}
