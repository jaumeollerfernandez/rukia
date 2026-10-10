package com.rukia.phone.infrastructure.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.infrastructure.media.rememberCaseImage

private val Band = Color(0xFFDADDE2)
private val Rule = Color(0xFFB9BEC6)
private val Figure = Color(0xFF8B939E)

/**
 * A police photo, front or [profile], with its [plate] ("OLOT · 004417") at the bottom. The picture at [path] (in the
 * case's content) if there is one; until then, a silhouette against the height chart.
 */
@Composable
fun MugshotPicture(plate: String, profile: Boolean, path: String?, description: String, modifier: Modifier = Modifier) {
    val image = path?.let { rememberCaseImage(it) }
    Box(modifier.semantics { contentDescription = description }) {
        if (image != null) Image(image, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        else Canvas(Modifier.fillMaxSize()) {
            val band = size.height / 8
            var y = 0f
            while (y < size.height) {
                drawRect(Band, Offset(0f, y), Size(size.width, band))
                drawRect(Rule, Offset(0f, y + band - 1.dp.toPx()), Size(size.width, 1.dp.toPx()))
                y += band
            }
            val headY = size.height * 0.36f
            if (profile) {
                drawOval(Figure, Offset(size.width * 0.32f, headY - size.height * 0.13f), Size(size.width * 0.3f, size.height * 0.26f))
                drawOval(Figure, Offset(size.width * 0.58f, headY - size.height * 0.02f), Size(size.width * 0.07f, size.height * 0.06f)) // nose
                drawOval(Figure, Offset(size.width * 0.12f, size.height * 0.72f), Size(size.width * 0.68f, size.height * 0.56f))
            } else {
                drawCircle(Figure, size.width * 0.17f, Offset(size.width / 2, headY))
                drawOval(Figure, Offset(size.width * 0.06f, size.height * 0.72f), Size(size.width * 0.88f, size.height * 0.56f))
            }
        }
        Text(
            plate, Modifier.align(Alignment.BottomCenter).padding(bottom = 4.dp).background(Color.Black).padding(horizontal = 4.dp, vertical = 2.dp),
            color = Color.White, fontSize = 8.sp, fontFamily = FontFamily.Monospace, maxLines = 1, softWrap = false,
        )
    }
}
