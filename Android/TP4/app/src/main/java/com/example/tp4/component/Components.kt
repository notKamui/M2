package com.example.tp4.component

import android.graphics.RectF
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.tp4.model.Town
import kotlin.math.cos

private val MAP_BOUNDS = RectF(
    -5.6126f /* min longitude */,
    51.8073f /* max latitude */,
    8.3117f /* max longitude */,
    41.3509f /* min latitude */
)

private fun Town.project(): Pair<Float, Float> {
    val x = 1f - ((latitude - MAP_BOUNDS.bottom) / (MAP_BOUNDS.top - MAP_BOUNDS.bottom))
    val y = (longitude - MAP_BOUNDS.left) / (MAP_BOUNDS.right - MAP_BOUNDS.left)
    return x to y
}

@Composable
fun TownDisplayer(towns: Set<Town>, image: ImageBitmap) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
            .border(2.dp, Color.Black)
            .onSizeChanged { size = it }
    ) {
        Canvas(Modifier) {
            drawImage(image, dstSize = size)
            towns.forEach { town ->
                val (x, y) = town.project()
                val (actualX, actualY) = x * size.height to y * size.width
                drawCircle(Color.Cyan, center = Offset(actualX, actualY), radius = 10f)
            }
        }
    }
}
