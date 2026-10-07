package com.rukia.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import com.rukia.R

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

/** Home wallpaper ("Night"): violet top right, magenta bottom left, on ink. */
fun Modifier.wallpaper() = glow(Kit.Ink, Glow(Color(0xFF6A4BE0), 0.9f, 0.08f, 1.1f), Glow(Color(0xFFB83C74), 0f, 0.95f, 0.95f))

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
    val Exit = icon("M14 4h5v16h-5", "M10 8l-4 4 4 4", "M6 12h10")
    val Warning = icon("M12 3l10 18H2z", "M12 10v4", "M12 17.5v.5")
    val ArrowRight = icon("M5 12h14", "M13 6l6 6-6 6")
    val ArrowLeft = icon("M19 12H5", "M11 6l-6 6 6 6")
    val Terminal = icon("M4 17l6-6-6-6", "M12 19h8")
    val Mail = icon("M3 6h18v12H3z", "M3 6l9 7 9-7")
    val Check = icon("M5 12.5l4.5 4.5L19 7.5")
    val Send = icon("M12 19V5", "M5 12l7-7 7 7")
    val Camera = icon("M4 8h3l2-3h6l2 3h3v11H4z", "M8.5 13a3.5 3.5 0 1 0 7 0a3.5 3.5 0 1 0 -7 0")
    val Speaker = icon("M4 9h4l5-4v14l-5-4H4z", "M16 9a4 4 0 0 1 0 6", "M18.5 6.5a7.5 7.5 0 0 1 0 11")
    val MicOff = icon("M12 3a3 3 0 0 1 3 3v5a3 3 0 0 1 -6 0v-5a3 3 0 0 1 3 -3z", "M5 11a7 7 0 0 0 14 0", "M12 18v3", "M4 4l16 16")
    val Play = icon("M8 5l11 7-11 7z", filled = true)
    val Stop = icon("M7 7h10v10H7z", filled = true)

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
