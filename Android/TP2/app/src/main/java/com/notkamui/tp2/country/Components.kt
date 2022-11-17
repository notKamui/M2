package com.notkamui.tp2.country

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.icu.util.TimeZone
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notkamui.tp2.R
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.coroutines.delay

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
                .fillMaxSize()
                .padding(8.dp),
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
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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

            val labels = @Composable {
                Column {
                    country.facts.keys.forEach { Text(text = it, fontSize = 20.sp) }
                }
            }

            ComputeWidth(labels) { width ->
                FactsDisplayer(facts = country.facts, labelsWidth = width)
            }
        }
        val (latitude, longitude) = country.capitalLocation
        val map by createMapWithLocation(latitude, longitude)
        map?.let { Image(bitmap = it, contentDescription = null) }
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

@Composable
fun FactsDisplayer(facts: Map<String, QuantitativeFact>, labelsWidth: Dp) {
    Column {
        facts.entries.forEach { (name, fact) ->
            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    modifier = Modifier.width(labelsWidth),
                    text = name,
                    color = Color.Black,
                    fontSize = 20.sp
                )
                RankedValueDisplayer(fact.value, fact.unit, fact.rank)
            }
        }
    }
}

@Composable
fun FlagDisplayer(country: Country, onClick: (Country) -> Unit = {}) {
    Column(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick(country) }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        country.flag()
        Text(text = country.name, fontSize = 20.sp)
    }
}

@Composable
fun FlagsDisplayer(countries: List<Country>, onClick: (Country) -> Unit = {}) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(countries.size) { idx ->
            FlagDisplayer(countries[idx], onClick)
        }
    }
}

@Composable
fun ComputeWidth(labels: @Composable () -> Unit, content: @Composable (width: Dp) -> Unit) {
    SubcomposeLayout { constraints ->
        val width = subcompose("labels") {
            labels()
        }[0].measure(Constraints()).width.toDp()
        val contentPlaceable = subcompose("content") { content(width) }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}

@Composable
fun createMapWithLocation(latitude: Float, longitude: Float): State<ImageBitmap?> {
    val context = LocalContext.current
    return produceState<ImageBitmap?>(initialValue = null, latitude, longitude) {
        val mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.equirectangular_world_map, BitmapFactory.Options().also { it.inMutable = true })
        val canvas = Canvas(mapBitmap)
        val radius = minOf(canvas.width, canvas.height) / 50f
        val x = (longitude + 180f) / 360f * canvas.width
        val y = (1f - (latitude + 90f) / 180f) * canvas.height
        val paint = Paint()
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = Color.Blue.toArgb()
        canvas.drawCircle(x, y, radius, paint)
        value = mapBitmap.asImageBitmap()
    }
}

private fun TimeZone.nowAsString() = ZonedDateTime
    .now(toZoneId())
    .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))

private fun TimeZone.toZoneId() = ZoneId.of(id)

@Preview(showBackground = true)
@Composable
fun CountryDisplayerPreview() {
    FlagsDisplayer(listOf(Country.France, Country.Japan, Country.Monaco, Country.Bahamas))
}