package com.rukia.phone

import androidx.compose.ui.res.stringResource
import com.rukia.R
import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.EffectCue
import com.rukia.chat.infrastructure.ChatModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.random.Random

/**
 * Screen effects the story plays with `#effect: <name>` on a line. To add one, write a composable that fills the
 * screen and calls `onDone` when it's over, and list it here under the name the story uses.
 */
val effects: Map<String, @Composable (onDone: () -> Unit) -> Unit> = mapOf(
    "hacked" to { HackedEffect(it) },
    "glitch" to { GlitchEffect(it) },
    "blackout" to { BlackoutEffect(it) },
)

/**
 * Plays each story effect once, when its line arrives, over whatever the phone shows (home screen or any app),
 * one after another. Effects whose line arrived while the game was closed play when the case is opened.
 */
@Composable
fun EffectsLayer(caseId: String) {
    val context = LocalContext.current.applicationContext
    val queue = remember(caseId) { mutableStateListOf<EffectCue>() }
    LaunchedEffect(caseId) {
        val chat = ChatModule.of(context, caseId)
        val playedFile = File(CaseFolders.saves(context, caseId, "phone"), "effects-played.txt")
        val played = withContext(Dispatchers.IO) { if (playedFile.exists()) playedFile.readLines().toMutableSet() else mutableSetOf() }
        // ponytail: reads every chat file each second; have the chat app announce arrivals if cases grow to many chats.
        while (true) {
            queue += withContext(Dispatchers.IO) {
                chat.listArrivedEffects().filter { played.add(it.id) }
                    .also { new -> if (new.isNotEmpty()) playedFile.appendText(new.joinToString("") { it.id + "\n" }) }
            }.filter { it.effect in effects } // unknown names are caught by EffectsTest
            delay(1_000)
        }
    }
    val cue = queue.firstOrNull() ?: return
    val play = effects.getValue(cue.effect)
    key(cue.id) {
        BackHandler {} // the effect owns the screen until it ends
        Box(Modifier.fillMaxSize().blockTouches()) { play { queue.remove(cue) } }
    }
}

private fun Modifier.blockTouches() = pointerInput(Unit) {
    awaitPointerEventScope { while (true) awaitPointerEvent().changes.forEach { it.consume() } }
}

private fun vibrate(context: Context, vararg pattern: Long) {
    context.getSystemService(Vibrator::class.java)?.vibrate(VibrationEffect.createWaveform(pattern, -1))
}

/** A frame counter that goes up every [frameMillis] for [frames] frames, then calls [onDone]. */
@Composable
private fun frames(frames: Int, frameMillis: Long = 80, onDone: () -> Unit): Int {
    var tick by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) { repeat(frames) { delay(frameMillis); tick++ }; onDone() }
    return tick
}

private const val CODE_CHARS = "0123456789ABCDEF#$%&@/\\<>"
private val HackerGreen = Color(0xFF00FF41)

/** The phone gets taken over: scrolling code, red flashes and a warning, ~5 s. */
@Composable
private fun HackedEffect(onDone: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { vibrate(context, 0, 300, 100, 500, 100, 300) }
    val tick = frames(60, onDone = onDone)
    val random = Random(tick)
    Box(Modifier.fillMaxSize().background(if (tick % 9 == 0) Color(0xFF500000) else Color.Black)) {
        Column(Modifier.fillMaxSize().offset(x = random.nextInt(-8, 9).dp)) {
            repeat(60) {
                val line = String(CharArray(60) { CODE_CHARS[random.nextInt(CODE_CHARS.length)] })
                Text(line, color = HackerGreen.copy(alpha = random.nextFloat()), fontFamily = FontFamily.Monospace, fontSize = 11.sp, maxLines = 1, softWrap = false)
            }
        }
        if (tick % 6 < 4) {
            Text(
                stringResource(R.string.system_compromised), Modifier.align(Alignment.Center).background(Color.Black).padding(16.dp),
                color = Color.Red, fontSize = 28.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace,
            )
        }
    }
}

/** A short burst of interference over the screen, which stays visible underneath, ~1 s. */
@Composable
private fun GlitchEffect(onDone: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) { vibrate(context, 0, 120, 60, 120) }
    val tick = frames(14, onDone = onDone)
    Canvas(Modifier.fillMaxSize()) {
        val random = Random(tick)
        if (tick % 4 == 0) drawRect(Color.White.copy(alpha = 0.2f))
        repeat(8) {
            val color = listOf(Color.Cyan, Color.Magenta, Color.White, Color.Black)[random.nextInt(4)]
            drawRect(color.copy(alpha = 0.6f), Offset(0f, random.nextFloat() * size.height), Size(size.width, 4f + random.nextFloat() * 50f))
        }
    }
}

/** The screen dies for a moment and comes back, ~3.5 s. */
@Composable
private fun BlackoutEffect(onDone: () -> Unit) {
    val alpha = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        alpha.animateTo(1f, tween(250))
        delay(2_500)
        alpha.animateTo(0f, tween(800))
        onDone()
    }
    Box(Modifier.fillMaxSize().graphicsLayer { this.alpha = alpha.value }.background(Color.Black))
}
