package com.rukia.game.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.rukia.R
import com.rukia.game.domain.model.AchievementGroup
import com.rukia.game.domain.model.CaseReport
import com.rukia.game.domain.model.EndingTier
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars

// A closed case: its card in the case list and its closing report (design: KimoCasesClosed, KimoReport).

private val EndingTier.color get() = when (this) {
    EndingTier.Good -> Term.Green
    EndingTier.Partial -> Term.Amber
    EndingTier.Bad -> Term.RedText
}

@Composable
private fun EndingTier.status() = stringResource(when (this) {
    EndingTier.Good -> R.string.status_good
    EndingTier.Partial -> R.string.status_partial
    EndingTier.Bad -> R.string.status_bad
})

@Composable
private fun EndingTier.label() = stringResource(when (this) {
    EndingTier.Good -> R.string.tier_good
    EndingTier.Partial -> R.string.tier_partial
    EndingTier.Bad -> R.string.tier_bad
})

@Composable
private fun AchievementGroup.title() = stringResource(when (this) {
    AchievementGroup.KeyClues -> R.string.group_key_clues
    AchievementGroup.FalseLeads -> R.string.group_false_leads
    AchievementGroup.Decisions -> R.string.group_decisions
})

/** A closed case in the list: its ending, a stamp, how much of the case was found, and the report or a replay. */
@Composable
internal fun ClosedCaseCard(report: CaseReport, onReport: () -> Unit, onReplay: () -> Unit) {
    val case = report.case
    val ending = report.ending
    val color = ending.tier.color
    Box(
        Modifier.fillMaxWidth().background(Term.Card).border(1.dp, color.copy(alpha = 0.6f))
            .drawBehind { drawRect(color, size = size.copy(width = 4.dp.toPx())) },
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.case_file, case.title), style = mono(11))
                Text("${stringResource(R.string.case_number, case.number)} · ${ending.tier.status()}", style = mono(11, color, FontWeight.SemiBold))
            }
            Text(case.headline, Modifier.padding(end = 120.dp), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 25.sp, lineHeight = 27.sp, color = Term.Text)
            Column(
                Modifier.fillMaxWidth().background(Color.White.copy(alpha = 0.04f))
                    .drawBehind { drawRect(color, size = size.copy(width = 2.dp.toPx())) }.padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(stringResource(R.string.ending_closed_at, ending.number, case.endings.size, ending.closedAt), style = mono(10))
                Text(ending.name, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = color)
            }
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(stringResource(R.string.completed), style = mono(11, Term.Body, spacing = 0f))
                    Text("${report.percent}%", style = mono(11, Term.Text, FontWeight.SemiBold, 0f))
                }
                Bar(report.percent / 100f, 6.dp, Term.Cyan)
                Text(
                    stringResource(R.string.progress_summary, report.achievements, case.achievements.size, report.endings, case.endings.size),
                    style = mono(11, spacing = 0f),
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlineButton(stringResource(R.string.view_report), Modifier.weight(1f), onClick = onReport)
                AmberButton(stringResource(R.string.replay), Modifier.weight(1f), onClick = onReplay)
            }
        }
        Stamp(ending.stamp, color, Modifier.align(Alignment.TopEnd).padding(top = 38.dp, end = 12.dp), 12)
    }
}

/** Asks before playing a closed case again: the week starts over, what was found is kept. */
@Composable
internal fun ReplayDialog(title: String, onCancel: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onCancel) {
        Column(
            Modifier.fillMaxWidth().background(Color(0xFF0C1620)).border(1.dp, Term.Amber).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(stringResource(R.string.replay_kicker), style = mono(11, Term.Amber, spacing = 1.5f))
            Text(stringResource(R.string.replay_title, title), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Term.Text)
            Text(stringResource(R.string.replay_text), fontFamily = Barlow, fontSize = 14.sp, lineHeight = 20.sp, color = Term.Body)
            Row(Modifier.padding(top = 4.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlineButton(stringResource(R.string.cancel).uppercase(), Modifier.weight(1f), Term.Muted.copy(alpha = 0.4f), onClick = onCancel)
                AmberButton(stringResource(R.string.replay), Modifier.weight(1f), icon = false, onClick = onConfirm)
            }
        }
    }
}

/** The closing report: the ending reached, what was found over every run, and a way to play the case again. */
@Composable
fun ReportScreen(report: CaseReport, onBack: () -> Unit, onReplay: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onBack)
    val case = report.case
    val ending = report.ending
    val color = ending.tier.color
    var confirming by rememberSaveable { mutableStateOf(false) }
    Column(
        Modifier.fillMaxSize().terminalBackdrop().safeDrawingPadding().verticalScroll(rememberScrollState())
            .padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        FlagStripe(3.dp)
        BackHeader(stringResource(R.string.report_kicker), case.title, onBack)

        Box(Modifier.fillMaxWidth().background(Term.Card).border(1.dp, color.copy(alpha = 0.6f))) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                case.facts.firstOrNull()?.let { Text(it.second.uppercase(), Modifier.padding(end = 130.dp), style = mono(11)) }
                Text(case.headline, Modifier.padding(end = 130.dp), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 26.sp, color = Term.Text)
                Text(stringResource(R.string.report_closed, ending.closedAt), style = mono(11, spacing = 0f))
            }
            Stamp(ending.stamp, color, Modifier.align(Alignment.TopEnd).padding(top = 18.dp, end = 12.dp), 14)
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionTitle(stringResource(R.string.report_ending))
            Column(
                Modifier.fillMaxWidth().background(Term.Card).border(1.dp, Term.Muted.copy(alpha = 0.25f))
                    .drawBehind { drawRect(color, size = size.copy(width = 4.dp.toPx())) }.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.ending_of, ending.number, case.endings.size), style = mono(12, color, FontWeight.SemiBold, 0f))
                    Text(ending.tier.label(), style = mono(11, spacing = 0f))
                }
                Text(ending.name, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 26.sp, lineHeight = 28.sp, color = Term.Text)
                if (case.endingVoice.isNotEmpty()) Column(
                    Modifier.fillMaxWidth().background(Term.Cyan.copy(alpha = 0.06f)).dashedBorder(Term.Cyan.copy(alpha = 0.3f))
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(stringResource(R.string.transcript, case.endingVoice), style = mono(10, Term.Cyan))
                    Text(ending.quote, style = mono(13, Term.Text, spacing = 0f).copy(lineHeight = 19.sp))
                }
            }
        }

        Row(
            Modifier.fillMaxWidth().background(Term.Card).border(1.dp, Term.Cyan.copy(alpha = 0.3f)).padding(16.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Ring(report.percent)
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Meter(stringResource(R.string.achievements), report.achievements, case.achievements.size, Term.Cyan)
                Meter(stringResource(R.string.endings), report.endings, case.endings.size, Term.Amber)
            }
        }

        Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val (falseLeads, allFalseLeads) = report.falseLeads
            Stat("${case.days}/${case.days}", stringResource(R.string.stat_days), Modifier.weight(1f))
            Stat("${report.record.squadsSent}/${report.record.squads}", stringResource(R.string.stat_squads), Modifier.weight(1f))
            Stat("$falseLeads/$allFalseLeads", stringResource(R.string.stat_false_leads), Modifier.weight(1f))
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            SectionTitle(stringResource(R.string.report_achievements))
            case.achievements.groupBy { it.group }.forEach { (group, achievements) ->
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(group.title(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 15.sp, letterSpacing = 2.sp, color = Term.Cyan)
                    achievements.forEach { AchievementRow(it.id in report.record.achievements, it.title, it.description) }
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            SectionTitle(stringResource(R.string.endings_found, report.endings, case.endings.size))
            case.endings.chunked(2).forEach { row ->
                Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    row.forEach { e ->
                        val found = e.number in report.record.endings
                        val current = e == ending
                        Column(
                            Modifier.weight(1f).fillMaxHeight().heightIn(min = 72.dp)
                                .background(if (current) Term.Amber.copy(alpha = 0.08f) else Color(0xD90C1620))
                                .border(1.dp, if (current) Term.Amber else Term.Muted.copy(alpha = if (found) 0.35f else 0.15f))
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(stringResource(R.string.ending_short, e.number), style = mono(10, if (found) e.tier.color else Term.Dim, FontWeight.SemiBold))
                                if (current) Text(stringResource(R.string.this_run), style = mono(10, Term.Amber))
                            }
                            Text(
                                if (found) e.name else stringResource(R.string.undiscovered),
                                fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 16.sp, lineHeight = 18.sp, color = if (found) Term.Text else Term.Dim,
                            )
                        }
                    }
                    if (row.size == 1) Spacer(Modifier.weight(1f))
                }
            }
        }

        Column(Modifier.padding(top = 4.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                Modifier.fillMaxWidth().heightIn(min = 56.dp).clip(CutCorners).background(Term.Amber)
                    .clickable(role = Role.Button) { confirming = true },
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
            ) {
                Icon(RukiaIcons.Replay, null, Modifier.size(20.dp), tint = Term.Ink)
                Text(stringResource(R.string.replay_case), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 20.sp, letterSpacing = 3.sp, color = Term.Ink)
            }
            Text(
                stringResource(R.string.still_missing, case.endings.size - report.endings, case.achievements.size - report.achievements),
                Modifier.fillMaxWidth(), fontFamily = Barlow, fontSize = 13.sp, lineHeight = 18.sp, color = Term.Muted, textAlign = TextAlign.Center,
            )
            OutlineButton(stringResource(R.string.back_to_cases), Modifier.fillMaxWidth().heightIn(min = 50.dp), fontSize = 17, onClick = onBack)
        }
    }
    if (confirming) ReplayDialog(case.title, onCancel = { confirming = false }) { confirming = false; onReplay() }
}

@Composable
private fun SectionTitle(text: String) = Text(text, style = mono(12, weight = FontWeight.SemiBold, spacing = 1.5f))

/** The rotated, double-ruled stamp of a closed case. */
@Composable
private fun Stamp(text: String, color: Color, modifier: Modifier, size: Int) {
    Text(
        text, modifier.rotate(-8f).background(Color(0x99070B12)).border(2.dp, color).padding(2.dp).border(1.dp, color)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        style = mono(size, color, FontWeight.SemiBold, 1.5f).copy(lineHeight = (size + 3).sp, textAlign = TextAlign.Center),
    )
}

@Composable
private fun Bar(fraction: Float, height: Dp, color: Color) {
    Box(Modifier.fillMaxWidth().height(height).background(Term.Muted.copy(alpha = 0.2f))) {
        Box(Modifier.fillMaxWidth(fraction.coerceIn(0f, 1f)).fillMaxHeight().background(color))
    }
}

/** How much of the case was found, as a ring with the percentage inside. */
@Composable
private fun Ring(percent: Int) {
    val label = "${stringResource(R.string.completed)} $percent%"
    Box(Modifier.size(120.dp).semantics(mergeDescendants = true) { contentDescription = label }, contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize().padding(4.dp)) {
            val stroke = Stroke(8.dp.toPx(), cap = StrokeCap.Butt)
            drawArc(Term.Muted.copy(alpha = 0.2f), 0f, 360f, false, style = stroke)
            drawArc(Term.Cyan, -90f, 360f * percent / 100f, false, style = stroke)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("$percent%", fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 34.sp, lineHeight = 34.sp, color = Term.Text)
            Text(stringResource(R.string.completed), style = mono(9))
        }
    }
}

@Composable
private fun Meter(label: String, found: Int, total: Int, color: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = mono(12, Term.Body, spacing = 0f))
            Text("$found/$total", style = mono(12, Term.Text, FontWeight.SemiBold, 0f))
        }
        Bar(if (total == 0) 0f else found.toFloat() / total, 4.dp, color)
    }
}

@Composable
private fun Stat(value: String, label: String, modifier: Modifier) {
    Column(modifier.fillMaxHeight().border(1.dp, Term.Muted.copy(alpha = 0.25f)).padding(10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(value, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 22.sp, color = Term.Text)
        Text(label, style = mono(10, spacing = 0f))
    }
}

/** An achievement: ticked if found in any run, else locked and hidden. */
@Composable
private fun AchievementRow(found: Boolean, title: String, description: String) {
    Row(
        Modifier.fillMaxWidth().heightIn(min = 44.dp)
            .background(if (found) Term.Cyan.copy(alpha = 0.06f) else Color.White.copy(alpha = 0.02f))
            .border(1.dp, if (found) Term.Cyan.copy(alpha = 0.3f) else Term.Muted.copy(alpha = 0.15f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(Modifier.size(28.dp).background(if (found) Term.Cyan else Term.Muted.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
            if (found) Icon(RukiaIcons.Check, null, Modifier.size(16.dp), tint = Term.Ground)
            else Icon(RukiaIcons.Lock, null, Modifier.size(14.dp), tint = Term.Dim)
        }
        Column(Modifier.weight(1f)) {
            Text(if (found) title else stringResource(R.string.achievement_hidden), fontFamily = Barlow, fontWeight = FontWeight.Medium, fontSize = 15.sp, color = if (found) Term.Text else Term.Dim)
            Text(if (found) description else stringResource(R.string.achievement_hidden_text), fontFamily = Barlow, fontSize = 13.sp, lineHeight = 17.sp, color = Term.Muted)
        }
    }
}

@Composable
private fun OutlineButton(label: String, modifier: Modifier, border: Color = Term.Cyan.copy(alpha = 0.55f), fontSize: Int = 16, onClick: () -> Unit) {
    Box(
        modifier.heightIn(min = 46.dp).border(1.dp, border).clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Text(label, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = fontSize.sp, letterSpacing = 2.sp, color = Term.Text) }
}

@Composable
private fun AmberButton(label: String, modifier: Modifier, icon: Boolean = true, onClick: () -> Unit) {
    Row(
        modifier.heightIn(min = 46.dp).background(Term.Amber).clickable(role = Role.Button, onClick = onClick),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
    ) {
        if (icon) Icon(RukiaIcons.Replay, null, Modifier.size(16.dp), tint = Term.Ink)
        Text(label, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 16.sp, letterSpacing = 2.sp, color = Term.Ink)
    }
}
