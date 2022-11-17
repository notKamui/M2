package com.notkamui.tp2.country

import android.icu.util.TimeZone
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notkamui.tp2.R

data class Country(
    val name: String,
    val image: Int,
    val timezone: TimeZone,
    val capitalLocation: Pair<Float, Float>,
    val area: AreaFact,
    val population: PopulationFact,
    val density: DensityFact,
    val perCapitalGDP: PerCapitalGDPFact,
    val flag: @Composable () -> Unit,
) {

    val facts = mapOf(
        "Area" to area,
        "Population" to population,
        "Density" to density,
        "GDP" to perCapitalGDP
    )

    companion object {

        val France = Country(
            name = "France",
            image = R.drawable.eiffel_tower,
            timezone = TimeZone.getTimeZone("Europe/Paris"),
            capitalLocation = Pair(48.8566f, 2.3522f),
            area = AreaFact(643801, Rank(42, 195)),
            population = PopulationFact(67939000, Rank(20, 195)),
            density = DensityFact(117, Rank(99, 248)),
            perCapitalGDP = PerCapitalGDPFact(2778090, Rank(7, 216)),
        ) { FrenchFlag() }

        val Japan = Country(
            name = "Japan",
            image = R.drawable.tori,
            timezone = TimeZone.getTimeZone("Asia/Tokyo"),
            capitalLocation = Pair(35.6895f, 139.6917f),
            area = AreaFact(377976, Rank(62, 195)),
            population = PopulationFact(125927902, Rank(11, 195)),
            density = DensityFact(332, Rank(12, 248)),
            perCapitalGDP = PerCapitalGDPFact(4300621, Rank(3, 216)),
        ) { JapanFlag() }

        val Monaco = Country(
            name = "Monaco",
            image = R.drawable.monaco,
            timezone = TimeZone.getTimeZone("Europe/Monaco"),
            capitalLocation = Pair(43.7384f, 7.4246f),
            area = AreaFact(2, Rank(194, 195)),
            population = PopulationFact(39150, Rank(190, 195)),
            density = DensityFact(18343, Rank(2, 248)),
            perCapitalGDP = PerCapitalGDPFact(6816, Rank(160, 216)),
        ) { MonacoFlag() }

        val Bahamas = Country(
            name = "Bahamas",
            image = R.drawable.columbus,
            timezone = TimeZone.getTimeZone("America/Nassau"),
            capitalLocation = Pair(25.0667f, -77.3333f),
            area = AreaFact(13943, Rank(155, 195)),
            population = PopulationFact(393450, Rank(170, 195)),
            density = DensityFact(29, Rank(193, 248)),
            perCapitalGDP = PerCapitalGDPFact(12693, Rank(146, 216)),
        ) { BahamasFlag() }

        fun all() = listOf(France, Japan, Monaco, Bahamas)
    }
}

@Composable
fun FrenchFlag() {
    Row(
        Modifier
            .aspectRatio(3f / 2f)
            .border(1.dp, Color.Black)
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .background(Color.Blue)
                .weight(1f / 3f, fill = true)
        )
        Box(
            Modifier
                .fillMaxHeight()
                .background(Color.White)
                .weight(1f / 3f, fill = true)
        )
        Box(
            Modifier
                .fillMaxHeight()
                .background(Color.Red)
                .weight(1f / 3f, fill = true)
        )
    }
}

@Composable
fun MonacoFlag() {
    Column(
        Modifier
            .aspectRatio(3f / 2f)
            .border(1.dp, Color.Black)
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .weight(1f / 2f, fill = true)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .weight(1f / 2f, fill = true)
        )
    }
}

@Composable
fun JapanFlag() {
    Box(
        modifier = Modifier
            .aspectRatio(3f / 2f)
            .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        Box(
            Modifier
                .fillMaxHeight(3f / 5f)
                .aspectRatio(1f)
                .background(Color.Red, shape = CircleShape)
        )
    }
}

@Composable
fun BahamasFlag() {
    val blue = Color(0, 173, 239)
    val yellow = Color(254, 242, 0)
    Box(
        Modifier
            .aspectRatio(3f / 2f)
            .border(1.dp, Color.Black)
    ) {
        Column(Modifier.fillMaxHeight()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(blue)
                    .weight(1f / 3f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(yellow)
                    .weight(1f / 3f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(blue)
                    .weight(1f / 3f, fill = true)
            )
        }
        val triangle = GenericShape { size, _ ->
            moveTo(0f, 0f)
            lineTo(size.width / 3f, size.height / 2f)
            lineTo(0f, size.height)
            close()
        }
        Box(
            Modifier
                .fillMaxSize()
                .clip(triangle)
                .background(Color.Black)
        )
    }
}