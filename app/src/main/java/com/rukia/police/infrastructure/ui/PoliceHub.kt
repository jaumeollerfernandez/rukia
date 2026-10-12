package com.rukia.police.infrastructure.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.chat.infrastructure.ui.ChannelAvatar
import com.rukia.game.infrastructure.ui.Barlow
import com.rukia.game.infrastructure.ui.CutCorners
import com.rukia.game.infrastructure.ui.Rajdhani
import com.rukia.game.infrastructure.ui.TermDialog
import com.rukia.game.infrastructure.ui.Term
import com.rukia.game.infrastructure.ui.dashedBorder
import com.rukia.game.infrastructure.ui.mono
import com.rukia.game.infrastructure.ui.terminalBackdrop
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.police.domain.model.Operation
import com.rukia.police.domain.model.OperationsBoard
import com.rukia.police.domain.model.SearchBoard
import com.rukia.police.domain.model.Squad
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// The Police app in the game's terminal look (design: PoliceHub, PoliceOps).

internal fun clock(millis: Long): String = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm"))

private val Panel = Color(0xE60C1620)
private val AmberTile = Brush.linearGradient(listOf(Color(0xFFF5BE52), Color(0xFFC98A12)))
private val CyanTile = Brush.linearGradient(listOf(Color(0xFF3FD0E0), Color(0xFF137C8A)))
private val GreyTile = Brush.linearGradient(listOf(Color(0xFF6B8299), Color(0xFF34465A)))
private val GreenTile = Brush.linearGradient(listOf(Color(0xFF4CC38A), Color(0xFF1F7A50)))

/**
 * The sergeant's channel inside the Police app: who she is, her unread lines, and the screen ([content]) that shows
 * the conversation. The phone wires it from the chat app, where the story lives.
 */
class PoliceChannel(val name: String, val status: String, val unread: () -> Int, val content: @Composable (onBack: () -> Unit) -> Unit)

/** What the hub knows of the case right now. */
internal class HubState(
    val now: Long, val search: SearchBoard, val board: OperationsBoard?, val readyRecords: Int?, val mapClosed: Boolean, val unheard: Int,
)

/** The Police app's first screen: the sergeant's channel, the case's day, what can be done today, and the tools. */
@Composable
internal fun PoliceHub(
    kicker: String, state: HubState, channel: PoliceChannel?, channelUnread: Int,
    onChannel: () -> Unit, onOps: () -> Unit, onMap: () -> Unit, onRecords: () -> Unit, onRecordings: () -> Unit,
    onReturnToTitle: () -> Unit, onReset: () -> Unit,
) {
    SystemBars(lightBottomIcons = true)
    var confirming by remember { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().terminalBackdrop().verticalScroll(rememberScrollState()).safeDrawingPadding()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(Modifier.size(46.dp).background(Color(0xFF0C1620)).border(1.dp, Term.Cyan.copy(alpha = 0.5f)), contentAlignment = Alignment.Center) {
                Icon(RukiaIcons.Shield, null, Modifier.size(26.dp), tint = Term.Cyan)
            }
            Column {
                Text(kicker, style = mono(10, Term.Cyan, spacing = 1.5f))
                Text(stringResource(R.string.police_hub_title), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 27.sp, lineHeight = 29.sp, color = Term.Text)
            }
        }
        channel?.let { ChannelCard(it, channelUnread, onChannel) }
        DayStrip(state.search, state.now)
        val squad = state.board?.squad
        if (squad != null || state.search.canSearch) Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val pulse by rememberInfiniteTransition(label = "pulse").animateFloat(1f, 0.35f, infiniteRepeatable(tween(800), RepeatMode.Reverse), label = "today")
                Box(Modifier.size(8.dp).alpha(pulse).background(Term.Amber, CircleShape))
                Text(stringResource(R.string.police_today), style = mono(12, Term.Amber, FontWeight.SemiBold, 1.5f))
            }
            if (squad != null) TodayCard(
                RukiaIcons.Car, Term.Amber, squad.title,
                stringResource(R.string.police_squad_until, squad.label, state.board?.squadUntil?.let(::clock) ?: "—"),
                stringResource(R.string.police_assign), onOps,
            )
            if (state.search.canSearch) TodayCard(
                RukiaIcons.Target, Term.Cyan, stringResource(R.string.police_zone), stringResource(R.string.police_zone_sub),
                stringResource(R.string.police_mark), onMap,
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(stringResource(R.string.police_tools), style = mono(12, Term.Muted, FontWeight.SemiBold, 1.5f))
            Row(Modifier.fillMaxWidth()) {
                if (state.board != null) Tool(RukiaIcons.Car, AmberTile, stringResource(R.string.operations), if (squad != null) 1 else 0, onClick = onOps)
                Tool(RukiaIcons.Pin, CyanTile, stringResource(R.string.police_map), if (state.search.canSearch) 1 else 0, enabled = !state.mapClosed, onClick = onMap)
                state.readyRecords?.let { Tool(RukiaIcons.IdCard, GreyTile, stringResource(R.string.police_records_short), it, onClick = onRecords) }
                Tool(RukiaIcons.Waveform, GreenTile, stringResource(R.string.police_recordings), state.unheard, onClick = onRecordings)
            }
        }
        Spacer(Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FooterButton(RukiaIcons.Exit, stringResource(R.string.return_title), Term.Body, Term.Muted.copy(alpha = 0.35f), Modifier.weight(1f), onReturnToTitle)
            FooterButton(RukiaIcons.Replay, stringResource(R.string.reset_chats), Color(0xFFFF8A8C), Term.RedText.copy(alpha = 0.45f), Modifier.weight(1f)) { confirming = true }
        }
    }
    if (confirming) TermDialog(
        stringResource(R.string.section_admin).uppercase(), stringResource(R.string.reset_q),
        stringResource(R.string.reset_text, com.rukia.phone.infrastructure.CaseClock.LATE_START_HOUR),
        stringResource(R.string.delete_everything).uppercase(), onCancel = { confirming = false },
        onConfirm = { confirming = false; onReset() },
    )
}

@Composable
private fun ChannelCard(channel: PoliceChannel, unread: Int, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(Panel).border(1.dp, Term.Cyan.copy(alpha = 0.45f)).clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ChannelAvatar(channel.name, 48, ring = Color(0xFF0C1620))
        Column(Modifier.weight(1f)) {
            Text(channel.name, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, lineHeight = 20.sp, color = Term.Text)
            Text(stringResource(R.string.police_channel_sub), fontFamily = Barlow, fontSize = 13.sp, color = Term.Body)
        }
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(3.dp)) {
            if (unread > 0) Badge(unread)
            Text(stringResource(R.string.police_online), style = mono(9, Term.Green, spacing = 0f))
        }
    }
}

@Composable
private fun Badge(count: Int) = Box(
    Modifier.defaultMinSize(minWidth = 20.dp).height(20.dp).background(Term.Amber, RoundedCornerShape(10.dp)).padding(horizontal = 5.dp),
    contentAlignment = Alignment.Center,
) { Text("$count", style = mono(11, Term.Ink, FontWeight.SemiBold, 0f)) }

/** "DAY 6 · 09:14", the days left, and the week as seven cells: past cyan, today amber, the last one ringed red. */
@Composable
private fun DayStrip(search: SearchBoard, now: Long) {
    val last = search.lastDay ?: 7
    Column(
        Modifier.fillMaxWidth().background(Panel).border(1.dp, Term.Cyan.copy(alpha = 0.3f)).padding(horizontal = 14.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
            Text(stringResource(R.string.police_day, search.today, clock(now)), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 1.sp, color = Term.Text)
            // The days left of the week, never the hour it ends: that's for the player to find out.
            val left = last - search.today
            Text(
                if (left > 0) pluralStringResource(R.plurals.police_days_left, left, left) else stringResource(R.string.police_last_day),
                style = mono(11, Term.Amber, spacing = 0f),
            )
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (d in 1..last) {
                val past = d < search.today
                val today = d == search.today
                Box(
                    Modifier.weight(1f).height(26.dp)
                        .background(if (today) Term.Amber else if (past) Term.Cyan.copy(alpha = 0.18f) else Term.Muted.copy(alpha = 0.08f))
                        .border(1.dp, if (d == last) Term.RedText.copy(alpha = 0.6f) else Color.Transparent),
                    contentAlignment = Alignment.Center,
                ) { Text("D$d", style = mono(11, if (today) Term.Ink else if (past) Term.Cyan else Term.Dim, FontWeight.SemiBold, 0f)) }
            }
        }
    }
}

@Composable
private fun TodayCard(icon: ImageVector, accent: Color, title: String, sub: String, action: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().background(accent.copy(alpha = 0.07f)).border(1.dp, accent.copy(alpha = 0.7f)).clickable(role = Role.Button, onClick = onClick).padding(12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(44.dp).background(accent), contentAlignment = Alignment.Center) { Icon(icon, null, Modifier.size(26.dp), tint = Term.Ink) }
        Column(Modifier.weight(1f)) {
            Text(title, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Term.Text)
            Text(sub, fontFamily = Barlow, fontSize = 13.sp, color = Term.Body)
        }
        Text("$action ›", style = mono(11, accent, FontWeight.SemiBold, 0f))
    }
}

/** A big app-style icon with its label, and a badge when something is waiting there. */
@Composable
private fun RowScope.Tool(icon: ImageVector, tile: Brush, label: String, badge: Int, enabled: Boolean = true, onClick: () -> Unit) {
    Box(Modifier.weight(1f).alpha(if (enabled) 1f else 0.4f).clickable(enabled = enabled, role = Role.Button, onClick = onClick).padding(vertical = 6.dp, horizontal = 2.dp)) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Box(Modifier.size(60.dp).clip(RoundedCornerShape(14.dp)).background(tile), contentAlignment = Alignment.Center) {
                Icon(icon, null, Modifier.size(32.dp), tint = Color.White)
            }
            Text(label, fontFamily = Barlow, fontSize = 13.sp, lineHeight = 15.sp, color = Term.Text, textAlign = TextAlign.Center)
        }
        if (badge > 0) Box(Modifier.align(Alignment.TopEnd).padding(end = 4.dp)) { Badge(badge) }
    }
}

@Composable
private fun FooterButton(icon: ImageVector, label: String, color: Color, border: Color, modifier: Modifier, onClick: () -> Unit) {
    Row(
        modifier.heightIn(min = 44.dp).border(1.dp, border).clickable(role = Role.Button, onClick = onClick).padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
    ) {
        Icon(icon, null, Modifier.size(16.dp), tint = color)
        Text(label.uppercase(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 15.sp, letterSpacing = 1.5.sp, color = color)
    }
}

private val OperationIcons = mapOf(
    "house" to RukiaIcons.House, "mountain" to RukiaIcons.Mountain, "tent" to RukiaIcons.Tent, "building" to RukiaIcons.Building,
    "bus" to RukiaIcons.Bus, "cuffs" to RukiaIcons.Cuffs, "city" to RukiaIcons.City, "volcano" to RukiaIcons.Volcano, "eye" to RukiaIcons.Eye,
)

/**
 * Operations: every squad of the case and its state, then every destination; those the open squad can't go to are
 * locked. Picking one and sending asks [onSend] once the player confirms. [timeOf] reads a case time ("D6 08:00").
 */
@Composable
internal fun OperationsScreen(
    board: OperationsBoard, now: Long, day: Int, timeOf: (String) -> Long?, failed: Boolean,
    onBack: () -> Unit, onSend: (Operation) -> Unit,
) {
    SystemBars(lightBottomIcons = true)
    var selected by remember(board.squad) { mutableStateOf<String?>(null) }
    var confirming by remember { mutableStateOf(false) }
    val squad = board.squad
    val sentOps = board.dispatched.map { it.operation }.toSet()
    Box(Modifier.fillMaxSize().terminalBackdrop()) {
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).safeDrawingPadding()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Box(Modifier.size(44.dp).border(1.dp, Term.Cyan.copy(alpha = 0.35f)).clickable(role = Role.Button, onClick = onBack), contentAlignment = Alignment.Center) {
                    Icon(RukiaIcons.ArrowLeft, stringResource(R.string.back), Modifier.size(22.dp), tint = Term.Text)
                }
                Column {
                    Text(stringResource(R.string.police_ops_kicker, day, clock(now)), style = mono(10, Term.Cyan, spacing = 1.5f))
                    Text(stringResource(R.string.operations), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 27.sp, lineHeight = 29.sp, color = Term.Text)
                }
            }
            board.squads.chunked(2).forEach { row ->
                Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { SquadCard(it, board, now, timeOf, Modifier.weight(1f).fillMaxHeight()) }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
            if (failed) Notice(Term.RedText, stringResource(R.string.no_squad_anymore))
            board.dispatched.lastOrNull()?.let { last ->
                val op = board.all.find { it.id == last.operation }
                Notice(Term.Green, stringResource(R.string.police_sent_to, op?.title?.ifEmpty { op.label } ?: last.operation))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.police_pick_destination), style = mono(12, Term.Muted, FontWeight.SemiBold, 1.5f))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("■ " + stringResource(R.string.police_patrol), style = mono(10, Term.Amber, spacing = 0f))
                    Text("■ " + stringResource(R.string.police_inspection), style = mono(10, Term.Cyan, spacing = 0f))
                }
            }
            board.all.chunked(2).forEach { row ->
                Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { op ->
                        val open = squad != null && board.operations.any { it.id == op.id }
                        OperationCard(op, open, sent = op.id in sentOps, selected = selected == op.id, Modifier.weight(1f).fillMaxHeight()) { selected = op.id }
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }
        val pick = board.all.find { it.id == selected }
        Box(
            Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                .background(Brush.verticalGradient(0f to Color.Transparent, 0.3f to Term.Ground)).navigationBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 16.dp),
        ) {
            val ready = squad != null && pick != null
            Box(
                Modifier.fillMaxWidth().heightIn(min = 56.dp).clip(CutCorners).background(if (ready) Term.Amber else Term.Muted.copy(alpha = 0.15f))
                    .clickable(enabled = ready, role = Role.Button) { confirming = true },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(if (squad == null) R.string.no_squad else if (pick == null) R.string.police_pick_one else R.string.police_send).uppercase(),
                    fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 19.sp, letterSpacing = 2.sp, color = if (ready) Term.Ink else Term.Muted,
                )
            }
        }
    }
    val pick = board.all.find { it.id == selected }
    if (confirming && pick != null && squad != null) TermDialog(
        stringResource(R.string.police_order), stringResource(R.string.police_send_q, pick.title.ifEmpty { pick.label }),
        stringResource(R.string.police_send_text, squad.label, pick.label),
        stringResource(R.string.send).uppercase(), onCancel = { confirming = false },
        onConfirm = { confirming = false; selected = null; onSend(pick) },
    )
}

@Composable
private fun SquadCard(squad: Squad, board: OperationsBoard, now: Long, timeOf: (String) -> Long?, modifier: Modifier) {
    val sent = board.dispatched.find { it.squad == squad.id }
    val from = timeOf(squad.from)
    val open = board.squad?.id == squad.id
    val (state, color) = when {
        sent != null -> stringResource(R.string.police_squad_sent, clock(sent.at)) to Term.Green
        open -> {
            val left = ((board.squadUntil ?: now) - now).coerceAtLeast(0) / 60_000
            stringResource(R.string.police_squad_open, left / 60, left % 60) to Term.Amber
        }
        from != null && now < from -> stringResource(R.string.police_squad_locked, clock(from)) to Term.Muted
        else -> stringResource(R.string.police_squad_gone) to Term.Dim
    }
    val lit = sent != null || open
    Column(
        modifier.background(if (lit) color.copy(alpha = 0.08f) else Color.Transparent)
            .then(if (lit) Modifier.border(1.dp, color) else Modifier.dashedBorder(Term.Muted.copy(alpha = 0.35f))).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(state, style = mono(10, color, spacing = 1f))
        Text(squad.title, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = if (lit) Term.Text else Term.Body)
        Text(squad.label, fontFamily = Barlow, fontSize = 13.sp, lineHeight = 17.sp, color = if (lit) Term.Body else Term.Muted)
        Text("${squad.from.substringAfter(' ')} – ${squad.until.substringAfter(' ')}", style = mono(11, if (lit) Term.Text else Term.Muted, spacing = 0f))
    }
}

@Composable
private fun OperationCard(op: Operation, open: Boolean, sent: Boolean, selected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    val patrol = op.type == "patrol"
    val accent = if (patrol) Term.Amber else Term.Cyan
    val dim = !open
    Column(
        modifier.heightIn(min = 128.dp)
            .background(if (selected) Term.Amber.copy(alpha = 0.12f) else Panel)
            .then(
                when {
                    selected -> Modifier.border(2.dp, Term.Amber)
                    dim -> Modifier.dashedBorder(Term.Muted.copy(alpha = 0.3f))
                    else -> Modifier.border(1.dp, Term.Cyan.copy(alpha = 0.25f))
                },
            )
            .clickable(enabled = open, role = Role.RadioButton, onClick = onClick).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Box(
                Modifier.size(44.dp).clip(RoundedCornerShape(10.dp))
                    .background(if (dim) Brush.linearGradient(listOf(Term.Muted.copy(alpha = 0.15f), Term.Muted.copy(alpha = 0.15f))) else if (patrol) AmberTile else CyanTile),
                contentAlignment = Alignment.Center,
            ) { Icon(OperationIcons[op.icon] ?: if (patrol) RukiaIcons.Shield else RukiaIcons.Search, null, Modifier.size(26.dp), tint = if (dim) Term.Dim else Term.Ink) }
            Text(
                stringResource(if (sent) R.string.police_tag_sent else if (dim) R.string.police_tag_locked else if (patrol) R.string.police_patrol else R.string.police_inspection),
                style = mono(9, if (sent) Term.Green else if (dim) Term.Dim else accent, spacing = 0.5f),
            )
        }
        Text(op.title.ifEmpty { op.label }, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 17.sp, lineHeight = 18.sp, color = if (dim) Term.Dim else Term.Text)
        if (op.place.isNotEmpty()) Text(op.place, fontFamily = Barlow, fontSize = 12.sp, lineHeight = 15.sp, color = Term.Muted)
    }
}

@Composable
private fun Notice(color: Color, text: String) {
    Row(
        Modifier.fillMaxWidth().background(color.copy(alpha = 0.1f)).border(1.dp, color).padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(RukiaIcons.Check, null, Modifier.size(20.dp), tint = color)
        Text(text, fontFamily = Barlow, fontSize = 14.sp, lineHeight = 19.sp, color = Term.Text)
    }
}
