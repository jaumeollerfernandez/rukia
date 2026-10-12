package com.rukia.police.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import com.rukia.game.infrastructure.ui.Barlow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.activity.compose.BackHandler
import com.rukia.phone.infrastructure.AppLaunch
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.police.domain.model.Operation
import com.rukia.police.domain.model.RecordStatus
import com.rukia.police.domain.model.RecordedCall
import com.rukia.police.domain.port.Radio
import com.rukia.police.domain.port.StoryFlags
import com.rukia.police.domain.port.StoryFacts
import com.rukia.police.infrastructure.PoliceModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val PoliceBlue = Color(0xFF1A237E)
internal val Danger = Color(0xFFD32F2F)

/** Colors of the Police app's screens that read them (records, alerts). */
internal class PolicePalette(
    val background: Color, val card: Color, val text: Color, val subText: Color, val preview: Color, val separator: Color,
    val chevron: Color, val red: Color, val tint: Color, val warnBackground: Color, val warnText: Color,
    val alert: Color, val alertSeparator: Color, val ctaOffBackground: Color, val ctaOffText: Color,
)

/** The Police app in the game's terminal look (design: Police v2): dark, cyan accents. */
internal val OmiPolice = PolicePalette(
    background = Color(0xFF070B12), card = Color(0xFF0C1620), text = Color(0xFFE6EDF3), subText = Color(0xFF8FA3B5), preview = Color(0xFFB5C3D0),
    separator = Color(0x333FD0E0), chevron = Color(0xFF5A6D7E), red = Color(0xFFFF6B6E), tint = Color(0xFF3FD0E0),
    warnBackground = Color(0x1AFF6B6E), warnText = Color(0xFFFF8A8C), alert = Color(0xFF0C1620), alertSeparator = Color(0x4D3FD0E0),
    ctaOffBackground = Color(0x268FA3B5), ctaOffText = Color(0xFF8FA3B5),
)

internal val LocalPolice = staticCompositionLocalOf { OmiPolice }

/**
 * Police Department app for the case [caseTitle]. [channel] is the sergeant's secure chat, inside the app.
 * [listCallRecordings] gives the calls (answered or from the voicemail) and [onCallHeard] marks one listened to, [onResetChats] wipes the chat app's save, [onSolved] tells the story
 * the case was solved, [onSearched] that a squad was sent to search on a case day, and [onRecordRead] that the player
 * read a police record (its story flag), [isStoryTrue] reads a story variable (what unlocks a vehicle's record), and
 * [onReturnToTitle] leaves the phone. The phone wires them in.
 */
@Composable
fun PoliceApp(
    caseId: String,
    caseTitle: String,
    channel: PoliceChannel?,
    listCallRecordings: () -> List<RecordedCall>,
    onCallHeard: (RecordedCall) -> Unit,
    onResetChats: () -> Unit,
    onSolved: () -> Unit,
    onSearched: (day: Int) -> Unit,
    onReport: (channel: String, knot: String) -> Unit,
    onRecordRead: (flag: String) -> Unit,
    isStoryTrue: (variable: String) -> Boolean,
    onReturnToTitle: () -> Unit,
) {
    val c = OmiPolice
    SystemBars(lightBottomIcons = true, lightTopIcons = true)
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { PoliceModule(context, caseId, save = { onResetChats() }, radio = Radio(onReport), flags = StoryFlags(onRecordRead), facts = StoryFacts(isStoryTrue)) }
    var solving by rememberSaveable { mutableStateOf(false) }
    var chatting by rememberSaveable { mutableStateOf(false) }
    var operating by rememberSaveable { mutableStateOf(false) }
    // A tapped notification of the sergeant's opens her channel (the phone passes on the chat id).
    LaunchedEffect(AppLaunch.argument) {
        if (AppLaunch.argument != null && channel != null) { AppLaunch.argument = null; chatting = true }
    }
    val unread by produceState(channel?.unread?.invoke() ?: 0, channel, chatting) { while (true) { value = channel?.unread?.invoke() ?: 0; delay(3_000) } }
    var listeningCalls by rememberSaveable { mutableStateOf(false) }
    var heard by remember { mutableIntStateOf(0) } // bumped after listening to a recording, so it's no longer new
    var browsingRecords by rememberSaveable { mutableStateOf(false) }
    var openRecord by rememberSaveable { mutableStateOf<String?>(null) }
    var requested by remember { mutableIntStateOf(0) } // bumped after asking for a record, to show it at once
    // Records come in with the case's clock: look again every few seconds.
    val records by produceState(m.getRecords(), m, requested) { while (true) { value = m.getRecords(); delay(5_000) } }
    var failed by remember { mutableStateOf(false) } // the squad went off shift before it was sent
    // Squads come and go with the case's clock: look again every few seconds.
    var sent by remember { mutableIntStateOf(0) } // bumped after sending a squad, to show it at once
    val board by produceState(m.getOperations(), m, sent) { while (true) { value = m.getOperations(); delay(5_000) } }
    var searched by remember { mutableIntStateOf(0) } // bumped after a search, to show it at once
    val searches by produceState(m.getSearches(), m, searched) { while (true) { value = m.getSearches(); delay(1_000) } }
    // The result that solves it may come in while the app is closed: tell the story when it's first seen. The case itself
    // closes with the story's ending.
    LaunchedEffect(searches.solved) { if (searches.solved) onSolved() }

    val scheme = darkColorScheme().copy(primary = c.tint, background = c.background, surface = c.card)
    CompositionLocalProvider(LocalPolice provides c) {
        MaterialTheme(colorScheme = scheme) {
            CompositionLocalProvider(LocalContentColor provides c.text, LocalTextStyle provides LocalTextStyle.current.copy(fontFamily = Barlow)) {
                when {
                    searches.solved -> VerdictScreen(caseTitle, onReturnToTitle)
                    chatting && channel != null -> channel.content { chatting = false }
                    operating -> {
                        BackHandler { operating = false; failed = false }
                        OperationsScreen(board, CaseClock.now(caseId), searches.today, m::timeOf, failed, onBack = { operating = false; failed = false }) { op ->
                            // The squad may have gone off shift while the order was being confirmed.
                            failed = runCatching { m.dispatchSquad(op.id) }.isFailure
                            sent++
                        }
                    }
                    listeningCalls -> {
                        val calls = remember(heard) { listCallRecordings() }
                        CallRecordingsScreen(
                            calls, calls.filter { !it.heard }.map { it.audio }.toSet(), m::caseTimeOf,
                            onHeard = { onCallHeard(it); heard++ }, onBack = { listeningCalls = false },
                        )
                    }
                    openRecord != null -> {
                        val opened = remember(openRecord) { runCatching { m.readRecord(openRecord!!) }.getOrNull() }
                        if (opened == null) LaunchedEffect(Unit) { openRecord = null }
                        else RecordScreen(
                            caseTitle, opened,
                            canOpen = { id -> records.rows.any { it.record.id == id && it.request?.let { r -> CaseClock.now(caseId) >= r.readyAt } == true } },
                            isRead = { id -> records.rows.any { it.record.id == id && it.status == RecordStatus.Read } },
                            onBack = { openRecord = null; requested++ },
                            onOpen = { openRecord = it },
                        )
                    }
                    browsingRecords -> RecordsScreen(
                        caseTitle, records, m::caseTimeOf, onBack = { browsingRecords = false },
                        onRequest = { id ->
                            // another one may have been asked for meanwhile
                            runCatching { m.requestRecord(id) }
                            requested++
                        },
                        onOpen = { openRecord = it },
                    )
                    solving -> SolveCaseScreen(caseTitle, m.getCaseQuestion(), remember { m.getKnownPlaces() }, searches, { CaseClock.now(caseId) }, onBack = { solving = false }, onFind = { m.findPlace(it) }) { at, radius ->
                        // the deadline may have passed while the alert was open
                        runCatching { m.searchZone(at, radius) }.onSuccess { onSearched(it.day) }
                        searched++
                    }
                    else -> {
                        val now = CaseClock.now(caseId)
                        // Past the case's deadline there's nothing left to solve, unless the last squad is still out.
                        val closed = m.deadline?.let { now > it } == true && searches.pending(now) == null
                        val unit = m.getCaseQuestion().unit
                        PoliceHub(
                            listOf(unit, stringResource(R.string.case_file, caseTitle)).filter { it.isNotEmpty() }.joinToString(" · "),
                            HubState(now, searches, board.takeIf { m.hasOperations }, records.rows.count { it.status == RecordStatus.Ready }.takeIf { m.hasRecords }, closed,
                                remember(heard, listeningCalls) { listCallRecordings().count { !it.heard } }),
                            channel, unread,
                            onChannel = { chatting = true }, onOps = { operating = true }, onMap = { solving = true },
                            onRecords = { browsingRecords = true }, onRecordings = { listeningCalls = true },
                            onReturnToTitle = onReturnToTitle,
                            onReset = {
                                m.resetSave.reset()
                                // Back to the case list: the week starts when the case is opened again (at midnight, from noon on).
                                onReturnToTitle()
                            },
                        )
                    }
                }
            }
        }
    }
}

/** Grouped list section: small caps header, rounded card, optional footer. */
@Composable
internal fun Section(title: String, footer: String? = null, content: @Composable ColumnScope.() -> Unit) {
    val c = LocalPolice.current
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title.uppercase(), Modifier.padding(horizontal = 16.dp), color = c.subText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.4.sp)
        Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(c.card), content = content)
        footer?.let { Text(it, Modifier.padding(horizontal = 16.dp), color = c.subText, fontSize = 13.sp, lineHeight = 18.sp) }
    }
}

@Composable
internal fun RowDivider() = HorizontalDivider(Modifier.padding(start = 62.dp), thickness = 0.5.dp, color = LocalPolice.current.separator)

/** Settings-style row: colored icon tile, title and subtitle, and a chevron (or [trailing]). */
@Composable
internal fun ActionRow(
    icon: ImageVector, tile: Color, title: String, subtitle: String,
    trailing: @Composable () -> Unit = { Icon(RukiaIcons.ChevronRight, null, Modifier.size(18.dp), tint = LocalPolice.current.chevron) },
    onClick: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth().clickable(role = Role.Button, onClick = onClick).padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Box(Modifier.size(32.dp).background(tile, RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, null, Modifier.size(18.dp), tint = Color.White)
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Medium)
            Text(subtitle, color = LocalPolice.current.subText, fontSize = 14.sp)
        }
        trailing()
    }
}

/** Header of a screen inside the app: "‹ Police" back on the left, [title] centered. */
@Composable
internal fun SubHeader(title: String, onBack: () -> Unit) {
    val c = LocalPolice.current
    Row(Modifier.fillMaxWidth().statusBarsPadding().padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Row(Modifier.width(90.dp).heightIn(min = 44.dp).clickable(role = Role.Button, onClick = onBack), verticalAlignment = Alignment.CenterVertically) {
            Icon(RukiaIcons.ChevronLeft, null, Modifier.size(24.dp), tint = c.tint)
            Text(stringResource(R.string.police), color = c.tint, fontSize = 17.sp)
        }
        Text(title, Modifier.weight(1f), fontSize = 17.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        Spacer(Modifier.width(90.dp))
    }
}

/** iOS-style alert: title, text, then Cancel and a red [confirm] side by side. */
@Composable
internal fun IosAlert(title: String, text: String, confirm: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    val c = LocalPolice.current
    Dialog(onDismissRequest = onDismiss) {
        Column(Modifier.width(280.dp).clip(RoundedCornerShape(16.dp)).background(c.alert)) {
            Column(Modifier.fillMaxWidth().padding(start = 18.dp, end = 18.dp, top = 20.dp, bottom = 16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(title, color = c.text, fontSize = 17.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Text(text, color = c.preview, fontSize = 14.sp, lineHeight = 19.sp, textAlign = TextAlign.Center)
            }
            HorizontalDivider(thickness = 0.5.dp, color = c.alertSeparator)
            Row(Modifier.height(46.dp)) {
                Box(Modifier.weight(1f).fillMaxHeight().clickable(role = Role.Button, onClick = onDismiss), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.cancel), color = c.tint, fontSize = 17.sp)
                }
                VerticalDivider(thickness = 0.5.dp, color = c.alertSeparator)
                Box(Modifier.weight(1f).fillMaxHeight().clickable(role = Role.Button, onClick = onConfirm), contentAlignment = Alignment.Center) {
                    Text(confirm, color = c.red, fontSize = 17.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
