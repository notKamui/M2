package com.example.tp4.component

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.tp4.model.Town

private val MAP_BOUNDS = RectF(
    -5.6126f /* min longitude */,
    51.8073f /* max latitude */,
    8.3117f /* max longitude */,
    41.3509f /* min latitude */
)

private fun Town.project(): Pair<Float, Float> {
    val x = (longitude - MAP_BOUNDS.left) / (MAP_BOUNDS.right - MAP_BOUNDS.left)
    val y = 1f - ((latitude - MAP_BOUNDS.bottom) / (MAP_BOUNDS.top - MAP_BOUNDS.bottom))
    return x to y
}

private fun DrawScope.drawPath(path: List<Town>, canvasSize: IntSize) {
    if (path.isEmpty()) return

    val closedPath = path + path.first()
    val pathPoints = closedPath.map { it.project() }
    val segments = pathPoints.zipWithNext()
    segments.forEach { (start, end) ->
        val (startX, startY) = start.first * canvasSize.height to start.second * canvasSize.width
        val (endX, endY) = end.first * canvasSize.height to end.second * canvasSize.width
        drawLine(
            Color.Red,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = 2f
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun TownDisplayer(towns: List<Town>, path: List<Town>, annotation: String, image: ImageBitmap) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val textMeasurer = rememberTextMeasurer()
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

                val townText = textMeasurer.measure(AnnotatedString(town.name))
                drawText(townText, topLeft = Offset(actualX, actualY))

            }
            drawPath(path, size)

            val textAnnotation = textMeasurer.measure(AnnotatedString(annotation))
            drawText(textAnnotation, topLeft = Offset(
                20f,
                size.height.toFloat() - 70f,
            ))
        }
    }
}
