package com.rukia.game.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.game.domain.model.GameCase
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.delay
import kotlin.math.floor

// The console's colors (design: CaseConnect).
private val ConsoleGround = Color(0xFF05080D)
private val ConsoleText = Color(0xFFC9D6E0)

/** One line of the session: colored parts, and a copy bar under it for the files pulled off the phone. */
private class LogLine(val parts: List<Pair<String, Color>>, val bar: Boolean = false)

/**
 * Opening a case: an ssh session into the missing person's phone that pulls its data, line by line, then fades out
 * for [onDone] to show the phone. Back skips it.
 */
@Composable
fun CaseConnect(case: GameCase, onDone: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onDone)
    val id = case.title
    val prompt = { cmd: String -> listOf("omi@k05" to Term.Green, ":" to Term.Muted, "~" to Term.Cyan, "$ " to Term.Muted, cmd to ConsoleText) }
    val ok = { text: String -> listOf("[  OK  ] " to Term.Green, text to ConsoleText) }
    val info = { text: String -> listOf("» " to Term.Cyan, text to ConsoleText) }
    val file = { name: String, what: String -> listOf("$name ".padEnd(20, '.') + " " to Term.Muted, what to ConsoleText) }
    val warn = stringResource(R.string.connect_warn) + " "
    // ponytail: the first fact is the case's place (LUGAR); add a field to GameCase if a case lists them otherwise.
    val place = case.facts.firstOrNull()?.second
    val lines = listOfNotNull(
        LogLine(prompt("ssh -p 2222 root@10.13.0.47")),
        LogLine(listOf(stringResource(R.string.connect_relay, id) to Term.Muted)),
        LogLine(ok(stringResource(R.string.connect_tunnel))),
        LogLine(ok(stringResource(R.string.connect_host))),
        LogLine(prompt("omi-extract --case $id --full")),
        LogLine(info(stringResource(R.string.connect_device))),
        LogLine(ok(stringResource(R.string.connect_unlock))),
        LogLine(info(stringResource(R.string.connect_mount)), bar = true),
        LogLine(file("contacts2.db", stringResource(R.string.connect_contacts)), bar = true),
        LogLine(file("msgstore.db", stringResource(R.string.connect_chats)), bar = true),
        LogLine(file("DCIM/Camera", stringResource(R.string.connect_gallery)), bar = true),
        LogLine(file("com.gonpi.app", stringResource(R.string.connect_gonpi)), bar = true),
        place?.let { LogLine(listOf(warn to Term.Amber, stringResource(R.string.connect_location, it) to ConsoleText)) },
        LogLine(ok(stringResource(R.string.connect_custody, id))),
        LogLine(prompt("omi-mirror --display --interactive")),
        LogLine(listOf(stringResource(R.string.connect_mirror) to Term.Cyan)),
    )
    var shown by remember { mutableIntStateOf(0) }
    val fade = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        lines.forEachIndexed { i, line ->
            delay(if (line.bar) 650 else if (i == 0) 300 else 260)
            shown = i + 1
        }
        delay(900)
        fade.animateTo(0f, tween(350))
        onDone()
    }
    Column(
        Modifier.fillMaxSize().alpha(fade.value).drawBehind {
            drawRect(ConsoleGround)
            drawRect(Brush.radialGradient(listOf(Term.Cyan.copy(alpha = 0.08f), Color.Transparent), Offset(size.width / 2, 0f), size.width * 0.8f))
            val scan = Color.White.copy(alpha = 0.03f)
            var y = 0f
            while (y < size.height) { drawLine(scan, Offset(0f, y), Offset(size.width, y), 1f); y += 3.dp.toPx() }
        }.safeDrawingPadding().padding(bottom = 16.dp),
    ) {
        Row(
            Modifier.fillMaxWidth().background(Color(0xFF0B121B)).drawBehind {
                drawLine(Term.Cyan.copy(alpha = 0.25f), Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
            }.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(3) { Box(Modifier.size(10.dp).background(Color(0xFF3A4654), CircleShape)) }
            }
            Text("omi@k05 — ssh $id", Modifier.weight(1f), style = mono(11, spacing = 0f).copy(textAlign = androidx.compose.ui.text.style.TextAlign.Center))
            Text(stringResource(R.string.connect_secure), style = mono(10, Term.Green, spacing = 0f))
        }
        Column(
            Modifier.weight(1f).fillMaxWidth().clipToBounds().semantics { liveRegion = LiveRegionMode.Polite }.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            lines.take(shown).forEachIndexed { i, line -> ConsoleLine(line, cursor = i == shown - 1) }
        }
        val percent = 100 * shown / lines.size
        Column(
            Modifier.fillMaxWidth().drawBehind { drawLine(Term.Cyan.copy(alpha = 0.2f), Offset.Zero, Offset(size.width, 0f), 1.dp.toPx()) }
                .padding(start = 14.dp, end = 14.dp, top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.connect_extraction, id), style = mono(10, spacing = 0f))
                Text("$percent%", style = mono(10, Term.Text, FontWeight.SemiBold, 0f))
            }
            Box(Modifier.fillMaxWidth().height(4.dp).background(Term.Muted.copy(alpha = 0.2f))) {
                Box(Modifier.fillMaxWidth(percent / 100f).fillMaxHeight().background(Term.Amber))
            }
        }
    }
}

/** A line slides in; the last one has the blinking cursor, and a file line fills its copy bar in ten steps. */
@Composable
private fun ConsoleLine(line: LogLine, cursor: Boolean) {
    val enter = remember { Animatable(0f) }
    LaunchedEffect(Unit) { enter.animateTo(1f, tween(180)) }
    Column(Modifier.graphicsLayer { alpha = enter.value; translationY = (1 - enter.value) * 3.dp.toPx() }, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        var blink by remember { mutableStateOf(true) }
        if (cursor) LaunchedEffect(Unit) { while (true) { delay(450); blink = !blink } }
        Text(
            buildAnnotatedString {
                line.parts.forEach { (text, color) -> withStyle(SpanStyle(color = color)) { append(text) } }
                if (cursor) withStyle(SpanStyle(color = if (blink) Term.Cyan else Color.Transparent)) { append("▌") }
            },
            style = mono(11, spacing = 0f).copy(fontSize = 11.5.sp, lineHeight = 16.sp),
        )
        if (line.bar) Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val copy = remember { Animatable(0f) }
            LaunchedEffect(Unit) { copy.animateTo(1f, tween(700, easing = LinearEasing)) }
            Box(Modifier.weight(1f).height(6.dp).background(Term.Cyan.copy(alpha = 0.12f))) {
                Box(Modifier.fillMaxWidth(floor(copy.value * 10) / 10).fillMaxHeight().background(Term.Cyan))
            }
            Text("100%", style = mono(10, Term.Green, spacing = 0f))
        }
    }
}

/**
 * The phone of case [caseTitle]. When [animate], it switches on like an old monitor: a bright line that opens to the
 * full screen, then "remote mirror active" for a moment at the top.
 */
@Composable
fun MirrorPowerOn(caseTitle: String, animate: Boolean, content: @Composable () -> Unit) {
    val open = remember { Animatable(if (animate) 0f else 1f) }
    LaunchedEffect(Unit) { open.animateTo(1f, tween(900, easing = LinearEasing)) }
    Box(Modifier.fillMaxSize().background(ConsoleGround)) {
        Box(
            Modifier.fillMaxSize().drawWithContent {
                val t = open.value
                // A thin line for the first third, then it opens up while the glare fades.
                val shown = if (t < 0.35f) 0.006f else 0.006f + 0.994f * (1 - (1 - (t - 0.35f) / 0.65f).let { it * it * it })
                val half = size.height * shown / 2
                clipRect(top = size.height / 2 - half, bottom = size.height / 2 + half) {
                    this@drawWithContent.drawContent()
                    drawRect(Color.White, alpha = (1 - t).coerceIn(0f, 1f) * 0.7f)
                }
            },
        ) { content() }
        val chip = remember { Animatable(0f) }
        if (animate) LaunchedEffect(Unit) {
            delay(600)
            chip.animateTo(1f, tween(390))
            delay(1_560)
            chip.animateTo(0f, tween(650))
        }
        Text(
            stringResource(R.string.connect_mirror_chip, caseTitle),
            Modifier.align(Alignment.TopCenter).safeDrawingPadding().padding(top = 10.dp)
                .graphicsLayer { alpha = chip.value; translationY = (chip.value - 1).coerceAtMost(0f) * 6.dp.toPx() }
                .background(Color(0xD905080D)).border(1.dp, Term.Cyan).padding(horizontal = 10.dp, vertical = 5.dp),
            style = mono(10, Term.Cyan, spacing = 1f),
        )
    }
}
