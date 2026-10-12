package com.rukia.game.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.delay
import kotlin.math.floor
import kotlin.math.roundToInt

private const val LOGO_MILLIS = 4_500L

/**
 * What the game shows when it starts: BARDWARE's logo (tap to skip), then, until the player has [consented] once, the
 * fiction notice, whose Continue calls [onConsent].
 */
@Composable
fun Splash(consented: Boolean, onConsent: () -> Unit, onDone: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    var notice by rememberSaveable { mutableStateOf(false) }
    var logoOver by remember { mutableStateOf(false) } // by a tap, back or the timer: whichever comes first
    val logoDone = {
        if (!logoOver) { logoOver = true; if (consented) onDone() else notice = true }
    }
    // Back skips the logo; on the notice it leaves the game as usual, since going on needs the consent.
    BackHandler(enabled = !notice, onBack = logoDone)
    LaunchedEffect(Unit) { delay(LOGO_MILLIS); logoDone() }
    AnimatedContent(notice, transitionSpec = { fadeIn(tween(600)) togetherWith fadeOut(tween(300)) }, label = "splash") { showNotice ->
        if (showNotice) FictionNotice { onConsent(); onDone() } else BardwareLogo(logoDone)
    }
}

/** The pixel monitor with green code raining on its screen and musical notes floating off (design: Splash, LogoBardware). */
@Composable
private fun BardwareLogo(onSkip: () -> Unit) {
    val skip = stringResource(R.string.splash_presents_skip)
    Column(
        Modifier.fillMaxSize().terminalBackdrop(glow = true).semantics { contentDescription = skip }
            .clickable(role = Role.Button, onClick = onSkip),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
    ) {
        val logo = ImageBitmap.imageResource(R.drawable.bardware_logo)
        val notes = listOf(R.drawable.bardware_note_a, R.drawable.bardware_note_b, R.drawable.bardware_note_c, R.drawable.bardware_note_d, R.drawable.bardware_note_e)
            .map { ImageBitmap.imageResource(it) }
        val time by produceState(0L) { val start = withFrameMillis { it }; while (true) withFrameMillis { value = it - start } }
        Canvas(Modifier.size(360.dp, 332.dp)) {
            val px = size.width / 400f // the logo's own pixels
            pixelImage(logo, Offset.Zero, Size(400 * px, 369 * px))
            codeRain(time / 1000f, px)
            NOTES.forEachIndexed { i, n -> note(notes[n.image], n, i, time / 1000f, px) }
        }
        val presents = remember { Animatable(0f) }
        LaunchedEffect(Unit) { delay(1_600); presents.animateTo(1f, tween(800)) }
        Text(
            stringResource(R.string.splash_presents), Modifier.padding(top = 18.dp).alpha(presents.value),
            fontFamily = Silkscreen, fontSize = 16.sp, letterSpacing = 4.sp, color = Term.Body,
        )
    }
}

private fun DrawScope.pixelImage(image: ImageBitmap, at: Offset, size: Size) = drawImage(
    image, dstOffset = IntOffset(at.x.roundToInt(), at.y.roundToInt()),
    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()), filterQuality = FilterQuality.None,
)

/** 13 columns of green dashes falling through the monitor's screen, each at its own pace. */
private fun DrawScope.codeRain(seconds: Float, px: Float) {
    val green = Brush.verticalGradient(0f to Color(0x0048AF45), 0.62f to Color(0xFF48AF45), 1f to Color(0xFF66FC5A), startY = 0f, endY = 120 * px)
    clipRect(164 * px, 112 * px, 244 * px, 186 * px) {
        for (i in 0 until 13) {
            val duration = 1.3f + ((i * 7) % 5) * 0.3f
            val delay = -((i * 0.53f) % 2.2f)
            val progress = floor(((seconds - delay) / duration).mod(1f) * 28) / 28 // steps(28)
            val x = (164 + 2 + i * 6) * px
            val top = 112 * px + (-120 + 200 * progress) * px
            translate(x, top) {
                // 4 px of green, 3 px of dark gap, as in the design's dashed column.
                var y = 0f
                while (y < 120) {
                    drawRect(green, Offset(0f, y * px), Size(4 * px, 4 * px))
                    drawRect(Color(0xFF28312C), Offset(0f, (y + 4) * px), Size(4 * px, 3 * px))
                    y += 7
                }
            }
        }
    }
}

private class Note(val image: Int, val x: Int, val y: Int, val w: Int, val h: Int)

// Where each note sits over the logo, in its pixels, and which of the five cut-outs it is.
private val NOTES = listOf(
    Note(0, 352, 71, 13, 31), Note(1, 300, 93, 13, 31), Note(2, 326, 102, 18, 26), Note(3, 273, 137, 18, 27),
    Note(1, 300, 137, 13, 31), Note(1, 344, 137, 13, 31), Note(4, 286, 172, 14, 31), Note(3, 317, 172, 18, 27),
)

/** A note that sways as it sings and drifts up and away, fading out, then starts over. */
private fun DrawScope.note(image: ImageBitmap, n: Note, i: Int, seconds: Float, px: Float) {
    val driftLength = 2f + (i % 3) * 0.4f
    val drift = ((seconds + i * 0.31f) / driftLength).mod(1f)
    val eased = drift * drift * drift // ease-in, close enough
    val alpha = if (drift < 0.7f) 1f else 1f - ((drift - 0.7f) / 0.3f).let { it * it * it }
    // Sing: four keyframes a cycle, each in six steps, as the design's steps(6).
    val sing = ((seconds - i * 0.11f) / 0.9f).mod(1f)
    val frames = listOf(0f to 0f, -7f to -12f, 0f to 0f, -4f to 10f, 0f to 0f)
    val k = (sing * 4).toInt().coerceAtMost(3)
    val local = floor((sing * 4 - k) * 6) / 6
    val lift = frames[k].first + (frames[k + 1].first - frames[k].first) * local
    val turn = frames[k].second + (frames[k + 1].second - frames[k].second) * local
    translate((n.x + 10 * eased) * px, (n.y - 26 * eased + lift) * px) {
        rotate(turn, Offset(n.w * px / 2, n.h * px)) {
            drawImage(
                image, dstSize = IntSize((n.w * px).roundToInt(), (n.h * px).roundToInt()),
                alpha = alpha, filterQuality = FilterQuality.None,
            )
        }
    }
}

/** "This is a work of fiction", with a note on the case's themes. Continue waits until the player ticks they understand. */
@Composable
private fun FictionNotice(onContinue: () -> Unit) {
    Column(
        Modifier.fillMaxSize().terminalBackdrop().safeDrawingPadding().padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 24.dp),
    ) {
        Column(
            Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.size(34.dp).border(1.dp, Term.Amber), contentAlignment = Alignment.Center) {
                    Icon(RukiaIcons.Warning, null, Modifier.size(18.dp), tint = Term.Amber)
                }
                Text(stringResource(R.string.notice_kicker), style = mono(12, Term.Amber, FontWeight.SemiBold, 3f))
            }
            Text(
                stringResource(R.string.notice_title), fontFamily = Rajdhani, fontWeight = FontWeight.Bold,
                fontSize = 34.sp, lineHeight = 36.sp, letterSpacing = 1.sp, color = Term.Text,
            )
            val body = Color(0xFFD3DCE4)
            Text(stringResource(R.string.notice_fiction), fontFamily = Barlow, fontSize = 17.sp, lineHeight = 25.sp, color = body)
            Text(stringResource(R.string.notice_resemblance), fontFamily = Barlow, fontSize = 17.sp, lineHeight = 25.sp, color = body)
            Text(stringResource(R.string.notice_agencies), fontFamily = Barlow, fontSize = 15.sp, lineHeight = 22.sp, color = Color(0xFF9FB0BF))
            Row(
                Modifier.fillMaxWidth().dashedBorder(Term.Muted.copy(alpha = 0.4f)).padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(stringResource(R.string.notice_note), style = mono(11, Term.Muted, FontWeight.SemiBold, 1f))
                Text(stringResource(R.string.notice_themes), fontFamily = Barlow, fontSize = 14.sp, lineHeight = 20.sp, color = Term.Body)
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            // The player has to say they understand it's fiction before going on.
            var ack by rememberSaveable { mutableStateOf(false) }
            Row(
                Modifier.fillMaxWidth().border(1.dp, if (ack) Term.Amber else Term.Muted.copy(alpha = 0.4f))
                    .background(if (ack) Term.Amber.copy(alpha = 0.08f) else Color(0x990C1620))
                    .toggleable(ack, role = Role.Checkbox) { ack = it }.padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Box(
                    Modifier.size(22.dp).border(2.dp, if (ack) Term.Amber else Term.Muted).background(if (ack) Term.Amber else Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) { if (ack) Icon(RukiaIcons.Check, null, Modifier.size(16.dp), tint = Term.Ink) }
                Text(stringResource(R.string.notice_ack), fontFamily = Barlow, fontSize = 15.sp, lineHeight = 21.sp, color = Term.Text)
            }
            // A slow bar under the text, to give it time to be read.
            val read = remember { Animatable(0f) }
            LaunchedEffect(Unit) { read.animateTo(1f, tween(6_000, easing = LinearEasing)) }
            Box(Modifier.fillMaxWidth().height(3.dp).background(Term.Muted.copy(alpha = 0.2f))) {
                Box(Modifier.fillMaxWidth(read.value).fillMaxHeight().background(Term.Cyan))
            }
            Row(
                Modifier.fillMaxWidth().heightIn(min = 54.dp).clip(CutCorners)
                    .then(if (ack) Modifier.background(Term.Amber) else Modifier.background(Term.Muted.copy(alpha = 0.12f)).border(1.dp, Term.Muted.copy(alpha = 0.35f), CutCorners))
                    .clickable(enabled = ack, role = Role.Button, onClick = onContinue),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    stringResource(R.string.notice_continue), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 3.sp,
                    color = if (ack) Term.Ink else Term.Muted,
                )
                if (ack) Icon(RukiaIcons.ArrowRight, null, Modifier.size(20.dp), tint = Term.Ink)
            }
            if (!ack) Text(stringResource(R.string.notice_ack_hint), Modifier.align(Alignment.CenterHorizontally), fontFamily = Barlow, fontSize = 12.sp, color = Term.Muted)
            Text("© BARDWARE · OMI", Modifier.align(Alignment.CenterHorizontally), style = mono(10, Term.Dim, spacing = 1f))
        }
    }
}
