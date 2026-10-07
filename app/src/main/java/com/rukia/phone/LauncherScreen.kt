package com.rukia.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import android.content.Context
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.round
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.police.domain.model.RecordedCall
import com.rukia.chat.infrastructure.ui.ChatApp
import com.rukia.game.cases
import com.rukia.gonpi.GonpiApp
import com.rukia.gonpi.GonpiPink
import com.rukia.multimedia.MultimediaApp
import com.rukia.multimedia.MultimediaOrange
import com.rukia.police.infrastructure.ui.PoliceApp
import com.rukia.police.infrastructure.ui.PoliceBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/** [badge] is the count shown on the icon (0 hides it); the home screen re-reads it every few seconds, off the main thread. */
class PhoneApp(
    val id: String, val label: String, val icon: ImageVector, val color: Color,
    val badge: () -> Int = { 0 },
    val content: @Composable () -> Unit,
)

/** Apps on the phone of case [caseId]. Add new ones here. [onReturnToTitle] leaves the phone for the game's title screen. */
fun installedApps(context: Context, caseId: String, onReturnToTitle: () -> Unit) = listOf(
    PhoneApp(
        "chat", "Chats", RukiaIcons.Chat, Color(0xFF25D366),
        badge = { ChatModule.of(context, caseId).listChats().count { it.unreadCount(CaseClock.now(caseId)) > 0 } },
    ) { ChatApp(caseId) },
    PhoneApp("police", "Police Department", RukiaIcons.Shield, PoliceBlue) {
        val case = cases.first { it.id == caseId }
        PoliceApp(
            caseId, caseLabel = "CASE ${case.number}", caseTitle = case.title,
            listCallRecordings = {
                val chat = ChatModule.of(context, caseId)
                chat.listCalls().filter { it.status == CallStatus.ANSWERED }.mapNotNull { call ->
                    call.audio?.let { RecordedCall(chat.characters[call.from]?.name ?: call.from, call.time, it) }
                }
            },
            onResetChats = {
                ChatModule.of(context, caseId).resetProgress()
                CaseClock.reset(context, caseId) // the week starts again the next time the case is opened
            },
            onSolved = { ChatModule.of(context, caseId).markCaseSolved() },
            onReport = { channel, knot -> ChatModule.of(context, caseId).playStoryEvent(channel, knot) },
            onReturnToTitle = onReturnToTitle,
        )
    },
    PhoneApp("multimedia", "Multimedia", Icons.Filled.Face, MultimediaOrange) { MultimediaApp(caseId) },
    PhoneApp("gonpi", "Gonpi", Icons.Filled.Favorite, GonpiPink) { GonpiApp(caseId) },
)


private const val COLUMNS = 4
private const val ROWS = 6
private val labelShadow = TextStyle(shadow = Shadow(Color.Black.copy(alpha = 0.5f), blurRadius = 6f))

/**
 * Cell (0 until COLUMNS * ROWS, row by row) of each app: its [saved] cell if it has one, else the first free cell.
 * Apps that don't fit are left out.
 */
internal fun arrange(appIds: List<String>, saved: Map<String, Int>): Map<String, Int> {
    val cells = saved.filter { (id, cell) -> id in appIds && cell in 0 until COLUMNS * ROWS }.toMutableMap()
    for (id in appIds) if (id !in cells) (0 until COLUMNS * ROWS).firstOrNull { it !in cells.values }?.let { cells[id] = it }
    return cells
}

/** Home screen: a clock and a COLUMNS x ROWS grid of apps. Tap an app to open it; long-press and drag to move it (dropping on another app swaps them). */
@Composable
fun LauncherScreen(apps: List<PhoneApp>, onOpen: (PhoneApp) -> Unit) {
    SystemBars(lightBottomIcons = true)
    // Each case's phone keeps its own layout, one "appId=cell" per line.
    val layoutFile = File(CaseFolders.saves(LocalContext.current, LocalCaseId.current, "phone"), "layout.txt")
    var cells by remember(layoutFile) {
        val saved = runCatching { layoutFile.readLines() }.getOrDefault(emptyList())
            .mapNotNull { line -> line.substringAfter('=').toIntOrNull()?.let { line.substringBefore('=') to it } }.toMap()
        mutableStateOf(arrange(apps.map { it.id }, saved))
    }
    var dragging by remember { mutableStateOf<String?>(null) }
    // ponytail: re-reads every badge each 3 s; have apps push counts if one gets expensive to count.
    val badges by produceState(emptyMap<String, Int>(), apps) {
        while (true) {
            value = withContext(Dispatchers.IO) { apps.associate { it.id to runCatching(it.badge).getOrDefault(0) } }
            delay(3_000)
        }
    }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    Column(
        Modifier.fillMaxSize()
            .wallpaper()
            .safeDrawingPadding()
            .padding(start = 20.dp, end = 20.dp, bottom = 16.dp),
    ) {
        ClockWidget()
        BoxWithConstraints(Modifier.fillMaxWidth().weight(1f).padding(top = 16.dp)) {
            val cellWidth = maxWidth / COLUMNS
            val cellHeight = maxHeight / ROWS
            val cellPx = with(LocalDensity.current) { Offset(cellWidth.toPx(), cellHeight.toPx()) }
            for (app in apps) {
                val cell = cells[app.id] ?: continue
                val isDragged = dragging == app.id
                AppIcon(
                    app, badges[app.id] ?: 0, cellHeight,
                    Modifier.size(cellWidth, cellHeight)
                        .offset(cellWidth * (cell % COLUMNS), cellHeight * (cell / COLUMNS))
                        .offset { if (isDragged) dragOffset.round() else IntOffset.Zero }
                        .zIndex(if (isDragged) 1f else 0f)
                        .graphicsLayer { if (isDragged) { scaleX = 1.15f; scaleY = 1.15f } }
                        // Taps outside, drag inside: the drag sees moves first, and a long press never counts as a tap.
                        .pointerInput(app) { detectTapGestures(onLongPress = {}, onTap = { onOpen(app) }) }
                        .pointerInput(app.id, cellPx) {
                            detectDragGesturesAfterLongPress(
                                onDragStart = { dragging = app.id; dragOffset = Offset.Zero },
                                onDrag = { change, amount -> change.consume(); dragOffset += amount },
                                onDragEnd = {
                                    val from = cells.getValue(app.id)
                                    val col = ((from % COLUMNS + 0.5f) + dragOffset.x / cellPx.x).toInt().coerceIn(0, COLUMNS - 1)
                                    val row = ((from / COLUMNS + 0.5f) + dragOffset.y / cellPx.y).toInt().coerceIn(0, ROWS - 1)
                                    val to = row * COLUMNS + col
                                    val other = cells.entries.firstOrNull { it.value == to }?.key
                                    cells = cells + (app.id to to) + listOfNotNull(other?.let { it to from })
                                    runCatching { layoutFile.writeText(cells.entries.joinToString("\n") { "${it.key}=${it.value}" }) }
                                    dragging = null
                                },
                                onDragCancel = { dragging = null },
                            )
                        }
                        .semantics(mergeDescendants = true) { role = Role.Button; onClick { onOpen(app); true } },
                )
            }
        }
    }
}

@Composable
private fun ClockWidget() {
    val clock = CaseClock.clock(LocalCaseId.current)
    var now by remember { mutableStateOf(LocalDateTime.now(clock).truncatedTo(ChronoUnit.MINUTES)) }
    LaunchedEffect(Unit) {
        // State only changes when the minute does, so this doesn't recompose every second.
        while (true) { delay(1_000); now = LocalDateTime.now(clock).truncatedTo(ChronoUnit.MINUTES) }
    }
    Column(Modifier.fillMaxWidth().padding(start = 6.dp, top = 24.dp)) {
        Text(
            now.format(DateTimeFormatter.ofPattern("HH:mm")), color = Color.White,
            fontSize = 82.sp, lineHeight = 86.sp, fontWeight = FontWeight.Light, letterSpacing = (-3).sp,
        )
        Text(now.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")), color = Color.White.copy(alpha = 0.86f), fontSize = 18.sp, fontWeight = FontWeight.Medium)
    }
}

/** Squircle tile from the kit (62 dp, 16 dp corners), shrunk on short screens so a 2-line label still fits in [cellHeight]. */
@Composable
private fun AppIcon(app: PhoneApp, badge: Int, cellHeight: Dp, modifier: Modifier) {
    Column(modifier.padding(vertical = 4.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        val iconSize = (cellHeight - 44.dp).coerceIn(36.dp, 62.dp)
        val shape = RoundedCornerShape(iconSize * 16f / 62f)
        Box {
            Box(
                Modifier.size(iconSize).shadow(10.dp, shape, ambientColor = Color.Black, spotColor = Color.Black.copy(alpha = 0.4f))
                    .background(app.color, shape)
                    // The kit's inner top highlight.
                    .background(Brush.verticalGradient(0f to Color.White.copy(alpha = 0.22f), 0.08f to Color.Transparent), shape),
                contentAlignment = Alignment.Center,
            ) { Icon(app.icon, null, Modifier.size(iconSize * 0.5f), tint = Color.White) }
            if (badge > 0) {
                Box(
                    Modifier.align(Alignment.TopEnd).offset(6.dp, (-6).dp).defaultMinSize(22.dp, 22.dp)
                        .background(Kit.Decline, RoundedCornerShape(11.dp)).padding(horizontal = 6.dp),
                    contentAlignment = Alignment.Center,
                ) { Text("$badge", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold) }
            }
        }
        Text(
            app.label, Modifier.padding(top = 5.dp), color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium,
            style = labelShadow, textAlign = TextAlign.Center, maxLines = 2, lineHeight = 14.sp,
        )
    }
}
