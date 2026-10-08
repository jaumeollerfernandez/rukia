package com.rukia.police.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.rukia.phone.CaseClock
import com.rukia.phone.Kit
import com.rukia.phone.RukiaIcons
import com.rukia.phone.SystemBars
import com.rukia.police.domain.model.Operation
import com.rukia.police.domain.model.RecordedCall
import com.rukia.police.domain.port.Radio
import com.rukia.police.domain.model.Verdict
import com.rukia.police.infrastructure.PoliceModule
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val PoliceBlue = Color(0xFF1A237E)
internal val Danger = Color(0xFFD32F2F)

/** Colors of the Police app, from the design canvas. It follows the device's dark mode. */
internal class PolicePalette(
    val background: Color, val card: Color, val text: Color, val subText: Color, val preview: Color, val separator: Color,
    val chevron: Color, val red: Color, val tint: Color, val warnBackground: Color, val warnText: Color,
    val alert: Color, val alertSeparator: Color, val ctaOffBackground: Color, val ctaOffText: Color,
)

private val LightPolice = PolicePalette(
    background = Color(0xFFF2F2F7), card = Color.White, text = Color(0xFF111114), subText = Color(0xFF6C6C70), preview = Color(0xFF3A3A3F),
    separator = Color(0xFFE2E2E7), chevron = Color(0xFFB4B4BA), red = Color(0xFFC62A20), tint = Kit.Tint,
    warnBackground = Color(0xFFFDECEA), warnText = Color(0xFFA1251B), alert = Color(0xFFF6F6F8), alertSeparator = Color(0xFFC9C9CF),
    ctaOffBackground = Color(0xFFDCDCE1), ctaOffText = Color(0xFF5C5C62),
)

private val DarkPolice = PolicePalette(
    background = Color.Black, card = Color(0xFF1C1C1E), text = Color(0xFFF2F2F7), subText = Color(0xFFA1A1A8), preview = Color(0xFFC4C4CA),
    separator = Color(0xFF38383C), chevron = Color(0xFF5A5A60), red = Color(0xFFFF6B60), tint = Color(0xFF6E9BFF),
    warnBackground = Color(0xFF3A1715), warnText = Color(0xFFFF9A90), alert = Color(0xFF2C2C30), alertSeparator = Color(0xFF48484E),
    ctaOffBackground = Color(0xFF2C2C30), ctaOffText = Color(0xFFA1A1A8),
)

internal val LocalPolice = staticCompositionLocalOf { LightPolice }

/**
 * Police Department app for the case shown as [caseLabel] ("CASE 0") and [caseTitle]. [listCallRecordings] gives the
 * answered calls, [onResetChats] wipes the chat app's save, [onSolved] tells the story the case was solved and
 * [onReturnToTitle] leaves the phone; [onCaseOver] tells the game the case has its verdict. The phone wires them in.
 */
@Composable
fun PoliceApp(
    caseId: String,
    caseLabel: String,
    caseTitle: String,
    listCallRecordings: () -> List<RecordedCall>,
    onResetChats: () -> Unit,
    onSolved: () -> Unit,
    onReport: (channel: String, knot: String) -> Unit,
    onReturnToTitle: () -> Unit,
    onCaseOver: () -> Unit,
) {
    val dark = isSystemInDarkTheme()
    val c = if (dark) DarkPolice else LightPolice
    SystemBars(lightBottomIcons = dark, lightTopIcons = dark)
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { PoliceModule(context, caseId, save = { onResetChats() }, onSolved = onSolved, radio = Radio(onReport)) }
    var confirming by remember { mutableStateOf(false) }
    var solving by rememberSaveable { mutableStateOf(false) }
    var listeningCalls by rememberSaveable { mutableStateOf(false) }
    var verdict by rememberSaveable { mutableStateOf<Verdict?>(null) }
    var picked by remember { mutableStateOf<Operation?>(null) }
    // Squads come and go with the case's clock: look again every few seconds.
    var sent by remember { mutableIntStateOf(0) } // bumped after sending a squad, to show it at once
    val board by produceState(m.getOperations(), m, sent) { while (true) { value = m.getOperations(); delay(5_000) } }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val scheme = (if (dark) darkColorScheme() else lightColorScheme()).copy(primary = c.tint, background = c.background, surface = c.card)
    CompositionLocalProvider(LocalPolice provides c) {
        MaterialTheme(colorScheme = scheme) {
            CompositionLocalProvider(LocalContentColor provides c.text) {
                val v = verdict
                when {
                    v != null -> VerdictScreen(v, onReturnToTitle)
                    listeningCalls -> CallRecordingsScreen(remember { listCallRecordings() }) { listeningCalls = false }
                    solving -> SolveCaseScreen(m.getCaseQuestion(), onBack = { solving = false }) { verdict = m.solveCase(it.id).also { onCaseOver() } }
                    else -> Box(Modifier.fillMaxSize().background(c.background)) {
                        Column(
                            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).statusBarsPadding().navigationBarsPadding()
                                .padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                            verticalArrangement = Arrangement.spacedBy(22.dp),
                        ) {
                            Text(
                                stringResource(R.string.app_police), Modifier.padding(start = 4.dp, top = 8.dp),
                                fontSize = 34.sp, lineHeight = 40.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.6).sp,
                            )
                            CaseCard(caseLabel, caseTitle, m.getCaseQuestion().question)
                            Section(stringResource(R.string.section_game)) {
                                // Past the case's deadline there's nothing left to solve.
                                val closed = m.deadline?.let { CaseClock.now(caseId) > it } == true
                                if (closed) ActionRow(RukiaIcons.Search, Danger, stringResource(R.string.solve_case), stringResource(R.string.solve_closed), trailing = {}) {}
                                else ActionRow(RukiaIcons.Search, Danger, stringResource(R.string.solve_case), stringResource(R.string.solve_one_try)) { solving = true }
                                RowDivider()
                                ActionRow(RukiaIcons.Exit, PoliceBlue, stringResource(R.string.return_title), stringResource(R.string.return_title_sub), onClick = onReturnToTitle)
                            }
                            if (m.hasOperations) OperationsSection(board, m.operationLabels) { picked = it }
                            Section(stringResource(R.string.section_evidence)) {
                                ActionRow(RukiaIcons.Phone, Kit.Accept, stringResource(R.string.call_recordings), stringResource(R.string.call_recordings_sub)) { listeningCalls = true }
                            }
                            Section(stringResource(R.string.section_admin), footer = stringResource(R.string.admin_footer)) {
                                Text(
                                    stringResource(R.string.reset_chats),
                                    Modifier.fillMaxWidth().clickable(role = Role.Button) { confirming = true }.heightIn(min = 48.dp)
                                        .padding(horizontal = 16.dp, vertical = 12.dp),
                                    color = c.red, fontSize = 17.sp, fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                        SnackbarHost(snackbar, Modifier.align(Alignment.BottomCenter).navigationBarsPadding())
                    }
                }

                picked?.let { op ->
                    val onWay = stringResource(R.string.officers_on_way)
                    val noneLeft = stringResource(R.string.no_squad_anymore)
                    IosAlert(
                        title = stringResource(R.string.send_officers_q),
                        text = stringResource(R.string.send_officers_text, board.squad?.label ?: stringResource(R.string.the_squad), op.label),
                        confirm = stringResource(R.string.send),
                        onDismiss = { picked = null },
                    ) {
                        picked = null
                        // The squad may have gone off shift while the alert was open.
                        val ok = runCatching { m.dispatchSquad(op.id) }.isSuccess
                        sent++
                        scope.launch { snackbar.showSnackbar(if (ok) onWay else noneLeft) }
                    }
                }

                if (confirming) {
                    val resetDone = stringResource(R.string.chats_reset)
                    IosAlert(
                        title = stringResource(R.string.reset_q),
                        text = stringResource(R.string.reset_text, CaseClock.LATE_START_HOUR),
                        confirm = stringResource(R.string.delete_everything),
                        onDismiss = { confirming = false },
                    ) {
                        confirming = false
                        m.resetSave.reset()
                        scope.launch { snackbar.showSnackbar(resetDone) }
                    }
                }
            }
        }
    }
}

@Composable
private fun CaseCard(label: String, title: String, question: String) {
    val c = LocalPolice.current
    Column(
        Modifier.fillMaxWidth().background(c.card, RoundedCornerShape(16.dp)).padding(horizontal = 16.dp, vertical = 22.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(Modifier.size(64.dp).background(PoliceBlue, RoundedCornerShape(17.dp)), contentAlignment = Alignment.Center) {
            Icon(RukiaIcons.Shield, null, Modifier.size(34.dp), tint = Color.White)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(label, color = c.subText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.6.sp)
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(question, color = c.preview, fontSize = 15.sp, lineHeight = 20.sp, textAlign = TextAlign.Center)
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
