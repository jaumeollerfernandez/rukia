package com.rukia.police.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.Glow
import com.rukia.phone.RukiaIcons
import com.rukia.phone.SystemBars
import com.rukia.phone.glow
import com.rukia.phone.rememberMediaImage
import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Suspect
import com.rukia.police.domain.model.Verdict

private val AccuseRed = Color(0xFFC62A20)

/** The question and its picture options. Picking only selects; the accusation needs a confirmation. */
@Composable
fun SolveCaseScreen(question: CaseQuestion, onBack: () -> Unit, onAccuse: (Suspect) -> Unit) {
    BackHandler(onBack = onBack)
    val c = LocalPolice.current
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var confirming by remember { mutableStateOf(false) }
    val selected = question.options.find { it.id == selectedId }

    Column(Modifier.fillMaxSize().background(c.background)) {
        SubHeader("Solve the case", onBack)
        LazyVerticalGrid(
            GridCells.Fixed(3),
            Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(Modifier.padding(bottom = 4.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(question.question, Modifier.padding(horizontal = 4.dp), fontSize = 28.sp, lineHeight = 33.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
                    Row(
                        Modifier.fillMaxWidth().background(c.warnBackground, RoundedCornerShape(14.dp)).padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Icon(RukiaIcons.Warning, null, Modifier.size(18.dp), tint = c.warnText)
                        Text("You only have one try. A wrong answer ends the game and erases this save file.", color = c.warnText, fontSize = 14.sp, lineHeight = 19.sp)
                    }
                }
            }
            items(question.options, key = { it.id }) { option ->
                OptionTile(option, selected = option.id == selectedId) { selectedId = option.id }
            }
        }
        Button(
            onClick = { confirming = true },
            enabled = selected != null,
            modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 16.dp).height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AccuseRed, contentColor = Color.White,
                disabledContainerColor = c.ctaOffBackground, disabledContentColor = c.ctaOffText,
            ),
        ) { Text(selected?.let { "Accuse ${it.label}" } ?: "Choose a suspect", fontSize = 17.sp, fontWeight = FontWeight.SemiBold) }
    }

    if (confirming && selected != null) {
        IosAlert(
            title = "Accuse ${selected.label}?",
            text = "This is your only attempt. If you're wrong, the game is over and all data in this save file is erased.",
            confirm = "Accuse",
            onDismiss = { confirming = false },
        ) { confirming = false; onAccuse(selected) }
    }
}

/** Square picture (or colored tile with the initial), ringed and checked when selected. */
@Composable
private fun OptionTile(option: Suspect, selected: Boolean, onClick: () -> Unit) {
    val c = LocalPolice.current
    Column(
        Modifier.selectable(selected, role = Role.RadioButton, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        // The ring sits outside the tile with a gap, as in the design; unselected tiles keep the same size.
        Box(Modifier.border(3.dp, if (selected) c.tint else Color.Transparent, RoundedCornerShape(28.dp)).padding(6.dp)) {
            Box(Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(22.dp))) {
                val image = rememberMediaImage(option.image)
                if (image != null) {
                    Image(image, option.label, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                } else {
                    // No picture in the media folders yet: colored tile with the initial.
                    Box(Modifier.fillMaxSize().background(Color(android.graphics.Color.parseColor(option.color))), contentAlignment = Alignment.Center) {
                        Text(option.label.removePrefix("The ").take(1).uppercase(), color = Color.White, fontSize = 40.sp, fontWeight = FontWeight.Bold)
                    }
                }
                if (selected) {
                    Box(
                        Modifier.align(Alignment.TopEnd).padding(6.dp).size(26.dp).border(2.dp, Color.White, CircleShape).padding(2.dp).background(c.tint, CircleShape),
                        contentAlignment = Alignment.Center,
                    ) { Icon(RukiaIcons.Check, null, Modifier.size(14.dp), tint = Color.White) }
                }
            }
        }
        Text(
            option.label, fontSize = 14.sp, lineHeight = 17.sp, fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            textAlign = TextAlign.Center, maxLines = 2,
        )
    }
}

/** Final screen after the accusation. The only way out is back to the title. */
@Composable
fun VerdictScreen(verdict: Verdict, onReturnToTitle: () -> Unit) {
    BackHandler {}
    SystemBars(lightBottomIcons = true)
    val solved = verdict == Verdict.SOLVED
    val accent = if (solved) Color(0xFFE0B354) else Color(0xFFEF5350)
    Column(
        Modifier.fillMaxSize()
            .glow(Color(0xFF0D1117), Glow(if (solved) Color(0xFF3A2F10) else Color(0xFF4A1212), 0.5f, 1f, 1f))
            .safeDrawingPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(if (solved) "CASE SOLVED" else "GAME OVER", color = accent, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 4.sp, textAlign = TextAlign.Center)
        Text(
            if (solved) "You found the truth.\nThe case is closed." else "Wrong accusation. The culprit got away.\nYour save file has been erased.",
            Modifier.padding(top = 16.dp), color = Color.White.copy(alpha = 0.82f), fontSize = 17.sp, lineHeight = 24.sp, textAlign = TextAlign.Center,
        )
        Button(
            onReturnToTitle, Modifier.padding(top = 48.dp).height(50.dp), shape = RoundedCornerShape(25.dp),
            contentPadding = PaddingValues(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color(0xFF0D1117)),
        ) { Text("Return to title", fontSize = 17.sp, fontWeight = FontWeight.Bold) }
    }
}
