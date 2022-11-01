package com.notkamui.tp2.country

import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notkamui.tp2.R
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RankedValueDisplayer(value: Int, unit: String, rank: Rank) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(rank.ratio, fill = true)
                    .background(Color.Green)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f - rank.ratio, fill = true)
                    .background(Color.White)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "$value$unit", color = Color.Black, fontSize = 20.sp)
            Text(
                text = "${rank.position} / ${rank.maxPosition}",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CountryDisplayer(country: Country) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f / 2f)) {
                country.flag()
            }
            Image(
                painter = painterResource(country.image),
                contentDescription = null,
                modifier = Modifier.weight(1f / 2f)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ClockDisplayer(country.timezone)
            country.facts.entries.forEach { (name, fact) ->
                Row(
                    Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier.weight(2f / 7f),
                        text = name,
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                    Box(modifier = Modifier.weight(5f / 7f)) {
                        RankedValueDisplayer(fact.value, fact.unit, fact.rank)
                    }
                }
            }
        }
    }
}

@Composable
fun ClockDisplayer(timezone: TimeZone) {
    var time by remember { mutableStateOf(timezone.nowAsString()) }
    LaunchedEffect(Unit) {
        while (true) {
            time = timezone.nowAsString()
            delay(1000)
        }
    }
    Text(
        text = time,
        color = Color.Black,
        fontSize = 20.sp
    )
}

private fun TimeZone.nowAsString() = ZonedDateTime
    .now(toZoneId())
    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))

private fun TimeZone.toZoneId() = ZoneId.of(id)

@Preview(showBackground = true)
@Composable
fun CountryDisplayerPreview() {
    CountryDisplayer(Country.France)
}