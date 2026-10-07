package com.rukia.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.CaseClock
import com.rukia.phone.RukiaIcons
import com.rukia.phone.SystemBars

/** Start: the case files there are so far. Back returns to the main menu. */
@Composable
fun TitleScreen(cases: List<GameCase>, onBack: () -> Unit, onPlay: (GameCase) -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    // A case file is active once its case has been opened.
    val active = remember(cases) { cases.count { it.kind == CaseKind.Case && CaseClock.isStarted(context, it.id) } }
    Column(
        Modifier.fillMaxSize().terminalBackdrop().safeDrawingPadding().padding(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        FlagStripe(3.dp)
        BackHeader(stringResource(R.string.cases_kicker), stringResource(R.string.choose_case), onBack)
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            cases.forEach { case ->
                when (case.kind) {
                    CaseKind.Case -> CaseFileCard(case) { onPlay(case) }
                    CaseKind.Training -> TrainingCard(case) { onPlay(case) }
                    CaseKind.Debug -> DebugCard(case) { onPlay(case) }
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(pluralStringResource(R.plurals.cases_footer, active, cases.size, active), style = mono(10, Term.Dim, spacing = 0.8f))
            Text(stringResource(R.string.agent_short), style = mono(10, Term.Dim, spacing = 0.8f))
        }
    }
}

/** A real case: file number, a PRIORITY stamp, the headline, its key facts and a short summary. */
@Composable
private fun CaseFileCard(case: GameCase, onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth().background(Term.Card).border(1.dp, Term.Amber.copy(alpha = 0.6f))
            .drawBehind { drawRect(Term.Amber, size = size.copy(width = 4.dp.toPx())) }
            .clickable(role = Role.Button, onClick = onClick),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.case_file, case.title), style = mono(11))
                Text(stringResource(R.string.case_number, case.number), style = mono(11, Term.Amber, FontWeight.SemiBold))
            }
            Text(
                case.headline, Modifier.padding(end = if (case.stamp != null) 150.dp else 0.dp),
                fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 25.sp, lineHeight = 27.sp, color = Term.Text,
            )
            if (case.facts.isNotEmpty()) {
                Column(Modifier.fillMaxWidth().dashedRules(Term.Muted.copy(alpha = 0.3f)).padding(vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    case.facts.chunked(2).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            row.forEach { (label, value) ->
                                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(label, style = mono(10))
                                    // The last fact (the deadline) stands out, as in the design.
                                    Text(value, fontFamily = Barlow, fontSize = 14.sp, lineHeight = 18.sp, color = if (label == case.facts.last().first) Term.Amber else Term.Text)
                                }
                            }
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
            Text(case.summary, fontFamily = Barlow, fontSize = 14.sp, lineHeight = 20.sp, color = Term.Body, maxLines = 3, overflow = TextOverflow.Ellipsis)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(Modifier.size(7.dp).background(Term.Green, CircleShape))
                    Text(stringResource(R.string.case_status_open), style = mono(11, Term.Green, spacing = 0f))
                }
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(stringResource(R.string.open_case_file), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 17.sp, letterSpacing = 2.sp, color = Term.Amber)
                    Icon(RukiaIcons.ArrowRight, null, Modifier.size(18.dp), tint = Term.Amber)
                }
            }
        }
        case.stamp?.let {
            Text(
                it, Modifier.align(Alignment.TopEnd).padding(top = 40.dp, end = 14.dp).rotate(-6f).border(2.dp, Term.Red).padding(horizontal = 8.dp, vertical = 3.dp),
                style = mono(11, Term.RedText, FontWeight.SemiBold, 1.5f),
            )
        }
    }
}

/** A training case: a lighter card with a cyan outline. */
@Composable
private fun TrainingCard(case: GameCase, onClick: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().background(Color(0xD90C1620)).border(1.dp, Term.Cyan.copy(alpha = 0.3f))
            .clickable(role = Role.Button, onClick = onClick).padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.training), style = mono(11, Term.Cyan))
            Text(stringResource(R.string.case_number, case.number), style = mono(11))
        }
        Text(case.headline, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 21.sp, lineHeight = 24.sp, color = Term.Text)
        Text(case.summary, fontFamily = Barlow, fontSize = 14.sp, lineHeight = 19.sp, color = Term.Body, maxLines = 2, overflow = TextOverflow.Ellipsis)
    }
}

/** A debug simulator: one dashed line with a terminal prompt. */
@Composable
private fun DebugCard(case: GameCase, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().dashedBorder(Term.Muted.copy(alpha = 0.45f)).clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(RukiaIcons.Terminal, null, Modifier.size(20.dp), tint = Term.Muted)
        Column(Modifier.weight(1f)) {
            Text(case.title, style = mono(12, Term.Text, FontWeight.SemiBold, 0f))
            Text(case.summary, fontFamily = Barlow, fontSize = 13.sp, color = Term.Body)
        }
    }
}
