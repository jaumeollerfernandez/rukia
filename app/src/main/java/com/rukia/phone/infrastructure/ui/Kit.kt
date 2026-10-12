package com.rukia.phone.infrastructure.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.rukia.R
import kotlin.math.roundToInt

/**
 * The remote phone's look ("Rukia OS kit" in the design canvas): Figtree, a few shared colors, the icon set and
 * the glass surfaces. Apps draw with these so the phone feels like one OS.
 */
object Kit {
    val Ink = Color(0xFF0D0F1E)
    val Tint = Color(0xFF1A63F0)
    val Accept = Color(0xFF23A84E)
    val Decline = Color(0xFFE5372C)
    val Status = Color(0xFFFFB45C)
    val Online = Color(0xFF2DBE60)
    val CallBackground = Color(0xFF0B0B14)
}

/** A [color] glow fading out from ([x], [y]) (fractions of the size) over [radius] × the width, on top of [base]. */
fun Modifier.glow(base: Color, vararg glows: Glow) = drawBehind {
    drawRect(base)
    for (g in glows) {
        drawRect(Brush.radialGradient(listOf(g.color, g.color.copy(alpha = 0f)), Offset(size.width * g.x, size.height * g.y), size.width * g.radius))
    }
}

class Glow(val color: Color, val x: Float, val y: Float, val radius: Float)

/**
 * Home wallpaper: the case's own [photo] (media/photos/wallpaper.jpg) cropped to fill the screen under a soft dark veil so the
 * icons and clock stay readable; without one, "Night": violet top right, magenta bottom left, on ink.
 */
fun Modifier.wallpaper(photo: ImageBitmap? = null) = if (photo == null) {
    glow(Kit.Ink, Glow(Color(0xFF6A4BE0), 0.9f, 0.08f, 1.1f), Glow(Color(0xFFB83C74), 0f, 0.95f, 0.95f))
} else drawBehind {
    drawRect(Kit.Ink)
    // Fill the screen keeping the photo's proportions, centred (what is cut off is the edge that overflows).
    val scale = maxOf(size.width / photo.width, size.height / photo.height)
    val w = (photo.width * scale).roundToInt()
    val h = (photo.height * scale).roundToInt()
    drawImage(photo, dstOffset = IntOffset(((size.width - w) / 2).roundToInt(), ((size.height - h) / 2).roundToInt()), dstSize = IntSize(w, h))
    drawRect(Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.45f), Color.Black.copy(alpha = 0.2f), Color.Black.copy(alpha = 0.5f))))
}

/** Call backdrop: the caller's color glowing from the top. */
fun Modifier.callBackdrop(tint: Color) = glow(Kit.CallBackground, Glow(tint, 0.5f, 0.18f, 1.1f))

private fun figtree(weight: Int) = Font(R.font.figtree, FontWeight(weight), variationSettings = FontVariation.Settings(FontVariation.weight(weight)))

val Figtree = FontFamily(figtree(300), figtree(400), figtree(500), figtree(600), figtree(700), figtree(800))

/** Material's type scale, all in Figtree. */
val FigtreeTypography = Typography().run {
    fun TextStyle.f() = copy(fontFamily = Figtree)
    Typography(
        displayLarge.f(), displayMedium.f(), displaySmall.f(), headlineLarge.f(), headlineMedium.f(), headlineSmall.f(),
        titleLarge.f(), titleMedium.f(), titleSmall.f(), bodyLarge.f(), bodyMedium.f(), bodySmall.f(),
        labelLarge.f(), labelMedium.f(), labelSmall.f(),
    )
}

/** Frosted glass from the kit: white at [alpha] with a 1 dp white hairline. (Real blur needs what's behind, so this is the API < 31 fallback look everywhere.) */
fun Modifier.glass(shape: Shape, alpha: Float = 0.14f) =
    background(Color.White.copy(alpha = alpha), shape).border(1.dp, Color.White.copy(alpha = 0.14f), shape)

/** Icons from the design, as 24-unit stroke paths (circles and rects written as arcs). */
object RukiaIcons {
    val Chat = icon("M21 11.5a8.4 8.4 0 0 1-12.3 7.5L3 21l2-5.6A8.4 8.4 0 1 1 21 11.5z")
    val ChatFilled = icon("M21 11.5a8.4 8.4 0 0 1-12.3 7.5L3 21l2-5.6A8.4 8.4 0 1 1 21 11.5z", filled = true)
    val Phone = icon("M5 4h4l2 5-2.5 1.5a11 11 0 0 0 5 5L15 13l5 2v4a2 2 0 0 1-2 2A16 16 0 0 1 3 6a2 2 0 0 1 2-2")
    val Person = icon("M8 8a4 4 0 1 0 8 0a4 4 0 1 0 -8 0", "M4 21a8 8 0 0 1 16 0")
    val PersonFilled = icon("M8 8a4 4 0 1 0 8 0a4 4 0 1 0 -8 0", "M4 21a8 8 0 0 1 16 0z", filled = true)
    val Shield = icon("M12 3l7 3v5c0 4.5-3 8.3-7 10-4-1.7-7-5.5-7-10V6z", "M12 8.5l1.2 2.4 2.6.4-1.9 1.8.5 2.6-2.4-1.3-2.4 1.3.5-2.6-1.9-1.8 2.6-.4z")
    val Search = icon("M4 11a7 7 0 1 0 14 0a7 7 0 1 0 -14 0", "M20 20l-3.5-3.5")
    val ChevronRight = icon("M9 5l7 7-7 7")
    val ChevronLeft = icon("M15 5l-7 7 7 7")
    val ChevronDown = icon("M5 9l7 7 7-7")
    val ChevronUp = icon("M5 15l7-7 7 7")
    val Close = icon("M6 6l12 12", "M18 6L6 18")
    val Exit = icon("M14 4h5v16h-5", "M10 8l-4 4 4 4", "M6 12h10")
    val Warning = icon("M12 3l10 18H2z", "M12 10v4", "M12 17.5v.5")
    val ArrowRight = icon("M5 12h14", "M13 6l6 6-6 6")
    val ArrowLeft = icon("M19 12H5", "M11 6l-6 6 6 6")
    val Terminal = icon("M4 17l6-6-6-6", "M12 19h8")
    val Mail = icon("M3 6h18v12H3z", "M3 6l9 7 9-7")
    val Check = icon("M5 12.5l4.5 4.5L19 7.5")
    val Replay = icon("M4 12a8 8 0 1 0 2.4-5.7", "M4 4v5h5")
    val Lock = icon("M5 11h14v10H5z", "M8 11V8a4 4 0 0 1 8 0v3")
    val IdCard = icon("M5 5h14a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7a2 2 0 0 1 2-2z", "M6.5 11a2.5 2.5 0 1 0 5 0a2.5 2.5 0 1 0 -5 0", "M5.5 16.5c.8-1.6 2-2.4 3.5-2.4s2.7.8 3.5 2.4", "M15 10h3.5M15 14h3.5")
    val Send = icon("M12 19V5", "M5 12l7-7 7 7")
    val Camera = icon("M4 8h3l2-3h6l2 3h3v11H4z", "M8.5 13a3.5 3.5 0 1 0 7 0a3.5 3.5 0 1 0 -7 0")
    val Speaker = icon("M4 9h4l5-4v14l-5-4H4z", "M16 9a4 4 0 0 1 0 6", "M18.5 6.5a7.5 7.5 0 0 1 0 11")
    val MicOff = icon("M12 3a3 3 0 0 1 3 3v5a3 3 0 0 1 -6 0v-5a3 3 0 0 1 3 -3z", "M5 11a7 7 0 0 0 14 0", "M12 18v3", "M4 4l16 16")
    val Play = icon("M8 5l11 7-11 7z", filled = true)
    val Stop = icon("M7 7h10v10H7z", filled = true)
    val Voicemail = icon("M3 12a3.5 3.5 0 1 0 7 0a3.5 3.5 0 1 0 -7 0", "M14 12a3.5 3.5 0 1 0 7 0a3.5 3.5 0 1 0 -7 0", "M6.5 15.5h11")
    // The Police app's tools and destinations (design: PoliceHub, PoliceOps).
    val Car = icon("M3 16v-3l2-5h14l2 5v3z", "M3 16h18v2H3z", "M5.2 18.5a1.8 1.8 0 1 0 3.6 0a1.8 1.8 0 1 0 -3.6 0", "M15.2 18.5a1.8 1.8 0 1 0 3.6 0a1.8 1.8 0 1 0 -3.6 0", "M10 5h4M12 3v2")
    val Target = icon("M4 12a8 8 0 1 0 16 0a8 8 0 1 0 -16 0", "M9 12a3 3 0 1 0 6 0a3 3 0 1 0 -6 0", "M12 2v3M12 19v3M2 12h3M19 12h3")
    val Pin = icon("M12 21s-6-5.6-6-10.5a6 6 0 0 1 12 0C18 15.4 12 21 12 21z", "M9.7 10.5a2.3 2.3 0 1 0 4.6 0a2.3 2.3 0 1 0 -4.6 0")
    val Waveform = icon("M4 12v0M7.5 8v8M11 5v14M14.5 9v6M18 7v10M21 11v2")
    val House = icon("M3 11l9-7 9 7", "M5 10v10h14V10", "M10 20v-6h4v6")
    val Mountain = icon("M3 20l6-10 4 6 3-4 5 8z")
    val Tent = icon("M3 20L12 4l9 16z", "M9 20l3-6 3 6")
    val Building = icon("M5 21V4h10v17", "M15 9h4v12", "M8 8h2M8 12h2M8 16h2", "M3 21h18")
    val Bus = icon("M5 4h14v13H5z", "M5 11h14", "M8 20v-3M16 20v-3")
    val Cuffs = icon("M3 15a4 4 0 1 0 8 0a4 4 0 1 0 -8 0", "M13 15a4 4 0 1 0 8 0a4 4 0 1 0 -8 0", "M7 11V8a5 5 0 0 1 10 0v3")
    val City = icon("M3 21h18", "M5 21V10l4-3v14", "M9 21V5h6v16", "M15 21v-9h4v9")
    val Volcano = icon("M3 20l5-9h8l5 9z", "M12 7V3M9 6L8 4M15 6l1-2")
    val Eye = icon("M2 12s4-7 10-7 10 7 10 7-4 7-10 7S2 12 2 12z", "M9 12a3 3 0 1 0 6 0a3 3 0 1 0 -6 0")

    private fun icon(vararg paths: String, filled: Boolean = false) =
        ImageVector.Builder(defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f).apply {
            for (d in paths) addPath(
                addPathNodes(d),
                fill = if (filled) SolidColor(Color.Black) else null,
                stroke = SolidColor(Color.Black), strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round,
            )
        }.build()
}
