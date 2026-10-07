package com.rukia.game

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.RukiaIcons

// The CNI terminal look of the menu, options and case list (design: "Rukia Phone Redesign", KimoMenu/KimoOptions/KimoCases).

internal object Term {
    val Ground = Color(0xFF070B12)
    val Cyan = Color(0xFF3FD0E0)
    val Amber = Color(0xFFF2B33D)
    val Ink = Color(0xFF0A0F16)
    val Text = Color(0xFFE6EDF3)
    val Body = Color(0xFFB5C3D0)
    val Muted = Color(0xFF8FA3B5)
    val Dim = Color(0xFF6E8294)
    val Red = Color(0xFFE5383B)
    val RedText = Color(0xFFFF6B6E)
    val Green = Color(0xFF7FD9A0)
    val Panel = Color(0xBF0A141E)
    val Card = Color(0xEB0C1620)
}

internal val Rajdhani = FontFamily(Font(R.font.rajdhani_semibold, FontWeight.SemiBold), Font(R.font.rajdhani_bold, FontWeight.Bold))
internal val PlexMono = FontFamily(Font(R.font.plex_mono_regular, FontWeight.Normal), Font(R.font.plex_mono_semibold, FontWeight.SemiBold))
internal val Barlow = FontFamily(Font(R.font.barlow_regular, FontWeight.Normal), Font(R.font.barlow_medium, FontWeight.Medium))

/** Small monospace label, as in the terminal's headers and footers. */
internal fun mono(size: Int, color: Color = Term.Muted, weight: FontWeight = FontWeight.Normal, spacing: Float = 1f) =
    TextStyle(fontFamily = PlexMono, fontSize = size.sp, color = color, fontWeight = weight, letterSpacing = spacing.sp)

/** The terminal's ground: a faint cyan grid, an optional glow behind the title, and scanlines. */
internal fun Modifier.terminalBackdrop(glow: Boolean = false) = drawBehind {
    drawRect(Term.Ground)
    if (glow) drawRect(
        Brush.radialGradient(listOf(Term.Cyan.copy(alpha = 0.14f), Color.Transparent), Offset(size.width / 2, size.height * 0.38f), size.width * 0.9f),
    )
    val step = 32.dp.toPx()
    val grid = Term.Cyan.copy(alpha = if (glow) 0.06f else 0.05f)
    var x = 0f
    while (x < size.width) { drawLine(grid, Offset(x, 0f), Offset(x, size.height), 1f); x += step }
    var y = 0f
    while (y < size.height) { drawLine(grid, Offset(0f, y), Offset(size.width, y), 1f); y += step }
    if (glow) {
        val scan = Color.White.copy(alpha = 0.025f)
        var s = 0f
        while (s < size.height) { drawLine(scan, Offset(0f, s), Offset(size.width, s), 1f); s += 3.dp.toPx() }
    }
}

/** Red-yellow-red stripe of the Spanish flag, on top of every terminal screen. */
@Composable
internal fun FlagStripe(height: Dp) {
    Row(Modifier.fillMaxWidth().height(height)) {
        Box(Modifier.weight(1f).fillMaxHeight().drawBehind { drawRect(Color(0xFFAA151B)) })
        Box(Modifier.weight(2f).fillMaxHeight().drawBehind { drawRect(Color(0xFFF1BF00)) })
        Box(Modifier.weight(1f).fillMaxHeight().drawBehind { drawRect(Color(0xFFAA151B)) })
    }
}

/** Square back button, a small cyan kicker and the screen's title. */
@Composable
internal fun BackHeader(kicker: String, title: String, onBack: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            Modifier.size(44.dp).border(1.dp, Term.Cyan.copy(alpha = 0.35f)).clickable(role = Role.Button, onClick = onBack),
            contentAlignment = Alignment.Center,
        ) { Icon(RukiaIcons.ArrowLeft, stringResource(R.string.back), Modifier.size(22.dp), tint = Term.Text) }
        Column {
            Text(kicker, style = mono(11, Term.Cyan, spacing = 2f))
            Text(title, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 28.sp, lineHeight = 30.sp, letterSpacing = 1.sp, color = Term.Text)
        }
    }
}

/** A 1 dp dashed outline, for the debug case and the credentials box. */
internal fun Modifier.dashedBorder(color: Color) = drawBehind {
    drawRect(color, style = Stroke(1.dp.toPx(), pathEffect = PathEffect.dashPathEffect(floatArrayOf(6.dp.toPx(), 4.dp.toPx()))))
}

/** Dashed rules above and below, like the facts grid of a case file. */
internal fun Modifier.dashedRules(color: Color) = drawBehind {
    val dash = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 3.dp.toPx()))
    drawLine(color, Offset(0f, 0f), Offset(size.width, 0f), 1f, pathEffect = dash)
    drawLine(color, Offset(0f, size.height), Offset(size.width, size.height), 1f, pathEffect = dash)
}

