package com.rukia.game.infrastructure.ui

import com.rukia.game.infrastructure.GameModule
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.delay

/** The first screen: KIMO over a radar, and Start, Options, Exit. */
@Composable
fun MainMenu(onStart: () -> Unit, onOptions: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    val activity = LocalActivity.current
    Column(
        Modifier.fillMaxSize().terminalBackdrop(glow = true).safeDrawingPadding().padding(start = 22.dp, end = 22.dp, top = 8.dp, bottom = 24.dp),
    ) {
        FlagStripe(4.dp)
        Row(Modifier.fillMaxWidth().padding(top = 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.cni_division), style = mono(11, spacing = 1.2f))
            Text(
                stringResource(R.string.restricted), Modifier.border(1.dp, Term.Red).padding(horizontal = 6.dp, vertical = 2.dp),
                style = mono(11, Term.RedText, FontWeight.SemiBold, 1.2f),
            )
        }
        AgentChip(Modifier.align(Alignment.End).padding(top = 18.dp))
        Box(Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
            Radar(Modifier.size(300.dp).offset(y = (-20).dp))
            Column(Modifier.offset(y = (-40).dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(stringResource(R.string.special_ops), style = mono(11, Term.Cyan, spacing = 3f))
                Text(
                    "KIMO", Modifier.padding(start = 20.dp), fontFamily = Rajdhani, fontWeight = FontWeight.Bold,
                    fontSize = 104.sp, lineHeight = 96.sp, letterSpacing = 20.sp, color = Color(0xFFF4F7FA),
                )
                Row {
                    Text(stringResource(R.string.channel_established), style = mono(12, spacing = 0f))
                    BlinkingCursor()
                }
            }
        }
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            MenuButton("01", stringResource(R.string.menu_start), primary = true, onClick = onStart)
            MenuButton("02", stringResource(R.string.menu_options), onClick = onOptions)
            MenuButton("03", stringResource(R.string.menu_exit), dim = true) { activity?.finish() }
        }
        Row(Modifier.fillMaxWidth().padding(top = 18.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.menu_coords), style = mono(10, Term.Dim, spacing = 0.8f))
            Text(stringResource(R.string.terminal_id), style = mono(10, Term.Dim, spacing = 0.8f))
        }
    }
}

/** Who's signed in: Agent Kimo, verified through Google Play Games (faked until it's added). */
@Composable
private fun AgentChip(modifier: Modifier) {
    Row(
        modifier.border(1.dp, Term.Cyan.copy(alpha = 0.35f)).background(Color(0xB30A141E)).padding(start = 7.dp, end = 12.dp, top = 7.dp, bottom = 7.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(Modifier.size(34.dp).background(Color(0xFF11303A)).border(1.dp, Term.Cyan), contentAlignment = Alignment.Center) {
            Icon(RukiaIcons.Person, null, Modifier.size(20.dp), tint = Term.Cyan)
        }
        Column {
            Text(stringResource(R.string.agent_kimo), style = mono(12, Term.Text, FontWeight.SemiBold, 0f))
            Text(stringResource(R.string.session_verified), style = mono(10, Term.Green, spacing = 0f))
        }
    }
}

/** Concentric rings with a slow sweep, behind the title. */
@Composable
private fun Radar(modifier: Modifier) {
    val sweep by rememberInfiniteTransition(label = "radar").animateFloat(0f, 360f, infiniteRepeatable(tween(6_000, easing = LinearEasing)), label = "sweep")
    Canvas(modifier) {
        val c = Offset(size.width / 2, size.height / 2)
        val unit = size.width / 300f
        val cyan = Term.Cyan.copy(alpha = 0.55f)
        drawCircle(cyan.copy(alpha = 0.55f * 0.25f), 140 * unit, c, style = Stroke(1f))
        drawCircle(cyan.copy(alpha = 0.55f * 0.35f), 104 * unit, c, style = Stroke(1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(3 * unit, 6 * unit))))
        drawCircle(cyan.copy(alpha = 0.55f * 0.2f), 66 * unit, c, style = Stroke(1f))
        val tick = cyan.copy(alpha = 0.55f * 0.5f)
        drawLine(tick, Offset(c.x, 0f), Offset(c.x, 40 * unit), 1f)
        drawLine(tick, Offset(c.x, size.height - 40 * unit), Offset(c.x, size.height), 1f)
        drawLine(tick, Offset(0f, c.y), Offset(40 * unit, c.y), 1f)
        drawLine(tick, Offset(size.width - 40 * unit, c.y), Offset(size.width, c.y), 1f)
        val r = 140 * unit
        drawArc(Term.Cyan.copy(alpha = 0.55f * 0.08f), sweep - 90f, 36f, useCenter = true, topLeft = Offset(c.x - r, c.y - r), size = Size(2 * r, 2 * r))
    }
}

@Composable
private fun BlinkingCursor() {
    var on by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) { while (true) { delay(550); on = !on } }
    Text("_", style = mono(12, if (on) Term.Cyan else Color.Transparent, spacing = 0f))
}

/** Button with its top-right and bottom-left corners cut off, like the design's primary one. */
internal val CutCorners = GenericShape { size, _ ->
    val cut = 14f * (size.height / 58f)
    moveTo(0f, 0f); lineTo(size.width - cut, 0f); lineTo(size.width, cut); lineTo(size.width, size.height)
    lineTo(cut, size.height); lineTo(0f, size.height - cut); close()
}

@Composable
private fun MenuButton(number: String, label: String, primary: Boolean = false, dim: Boolean = false, onClick: () -> Unit) {
    val base = Modifier.fillMaxWidth().heightIn(min = if (primary) 58.dp else 54.dp)
    val look = when {
        primary -> base.clip(CutCorners).background(Term.Amber)
        else -> base.border(1.dp, if (dim) Term.Muted.copy(alpha = 0.3f) else Term.Cyan.copy(alpha = 0.45f)).background(Color(0xBF0A141E))
    }
    Row(
        look.clickable(role = Role.Button, onClick = onClick).padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            number,
            style = mono(12, if (primary) Term.Ink.copy(alpha = 0.7f) else if (dim) Term.Muted else Term.Cyan, FontWeight.SemiBold, 0f),
        )
        Text(
            label.uppercase(), Modifier.weight(1f), fontFamily = Rajdhani, fontWeight = if (primary) FontWeight.Bold else FontWeight.SemiBold,
            fontSize = if (primary) 22.sp else 20.sp, letterSpacing = 3.sp,
            color = if (primary) Term.Ink else if (dim) Term.Body else Term.Text,
        )
        if (primary) Icon(RukiaIcons.ArrowRight, null, Modifier.size(22.dp), tint = Term.Ink)
    }
}

/** Options: for now, only the interface language. Picking one restarts the screen in it. */
@Composable
fun OptionsScreen(game: GameModule, onBack: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val activity = LocalActivity.current
    // Until the player picks one, the phone's language counts as picked if we have it.
    val current = game.getLanguage(context.resources.configuration.locales[0].language)
    Column(
        Modifier.fillMaxSize().terminalBackdrop().safeDrawingPadding().padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        FlagStripe(3.dp)
        BackHeader(stringResource(R.string.options_kicker), stringResource(R.string.options_title), onBack)
        Column(Modifier.padding(top = 8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.options_language).uppercase(), style = mono(12, weight = FontWeight.SemiBold, spacing = 1.5f))
            Column(Modifier.border(1.dp, Term.Cyan.copy(alpha = 0.3f)).background(Color(0xE60C1620)).selectableGroup()) {
                game.languages.forEach { (tag, name) ->
                    val on = tag == current
                    Row(
                        Modifier.fillMaxWidth().heightIn(min = 58.dp)
                            .background(if (on) Term.Amber.copy(alpha = 0.10f) else Color.Transparent)
                            .selectable(on, role = Role.RadioButton) {
                                if (!on) {
                                    game.chooseLanguage(tag)
                                    (activity as? Activity)?.recreate()
                                }
                            }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        Text(tag.substringBefore('-').uppercase(), Modifier.width(44.dp), style = mono(12, if (on) Term.Amber else Term.Muted, FontWeight.SemiBold, 0f))
                        Text(name, Modifier.weight(1f), fontFamily = Barlow, fontSize = 17.sp, color = Term.Text)
                        Box(Modifier.size(20.dp).border(2.dp, if (on) Term.Amber else Color(0xFF5A6D7E)), contentAlignment = Alignment.Center) {
                            if (on) Box(Modifier.size(10.dp).background(Term.Amber))
                        }
                    }
                }
            }
            Text(stringResource(R.string.options_language_note), Modifier.padding(horizontal = 2.dp), fontFamily = Barlow, fontSize = 13.sp, lineHeight = 18.sp, color = Term.Muted)
        }
        Spacer(Modifier.weight(1f))
        Column(Modifier.fillMaxWidth().dashedBorder(Term.Muted.copy(alpha = 0.35f)).padding(horizontal = 16.dp, vertical = 14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(stringResource(R.string.agent_kimo), style = mono(11, Term.Text, FontWeight.SemiBold, 0f))
            Text(stringResource(R.string.session_verified).removePrefix("● "), style = mono(11, spacing = 0f))
            Text(stringResource(R.string.credential), style = mono(11, spacing = 0f))
        }
    }
}

