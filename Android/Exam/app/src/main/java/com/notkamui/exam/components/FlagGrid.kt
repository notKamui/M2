package com.notkamui.exam.components

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.notkamui.exam.model.Country

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FlagGridDisplay(
    context: Context,
    countries: List<Country>,
    foundCountries: Set<Country>,
    onFlagClick: (Country) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), Orientation.Vertical),
        cells = GridCells.Fixed(3)
    ) {
        items(countries) { country ->
            FlagDisplay(context, country, country in foundCountries, onFlagClick)
        }
    }
}

@Composable
fun FlagDisplay(
    context: Context,
    country: Country,
    found: Boolean,
    onFlagClick: (Country) -> Unit
) {
    val flag = country.getFlag(context).asImageBitmap()
    val modifier = if (found) Modifier else Modifier.clickable { onFlagClick(country) }
    Column(modifier
        .padding(vertical = 8.dp)
        .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(bitmap = flag, contentDescription = "Flag of ${country.name}")
        Text(text = if (found) country.name else "???")
    }
}
