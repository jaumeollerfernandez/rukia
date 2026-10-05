package com.rukia.police.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.SystemBars
import com.rukia.phone.rememberMediaImage
import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Suspect
import com.rukia.police.domain.model.Verdict

/** The question and its picture options. Picking only selects; the accusation needs a confirmation. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolveCaseScreen(question: CaseQuestion, onBack: () -> Unit, onAccuse: (Suspect) -> Unit) {
    BackHandler(onBack = onBack)
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    var confirming by remember { mutableStateOf(false) }
    val selected = question.options.find { it.id == selectedId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Solve the case", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PoliceBlue, titleContentColor = Color.White, navigationIconContentColor = Color.White,
                ),
            )
        },
        bottomBar = {
            Button(
                onClick = { confirming = true },
                enabled = selected != null,
                modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(16.dp).height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Danger),
            ) { Text(selected?.let { "Accuse ${it.label}" } ?: "Choose an answer", fontSize = 16.sp) }
        },
        containerColor = Color(0xFFF5F6FA),
    ) { padding ->
        LazyVerticalGrid(
            GridCells.Fixed(2),
            Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Text(question.question, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Row(
                        Modifier.padding(top = 10.dp).background(Danger.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Filled.Warning, null, Modifier.size(18.dp), tint = Danger)
                        Text(
                            "You only have one try. A wrong answer ends the game and erases this save file.",
                            Modifier.padding(start = 8.dp), color = Danger, fontSize = 13.sp,
                        )
                    }
                }
            }
            items(question.options, key = { it.id }) { option ->
                OptionCard(option, selected = option.id == selectedId) { selectedId = option.id }
            }
        }
    }

    if (confirming && selected != null) {
        AlertDialog(
            onDismissRequest = { confirming = false },
            icon = { Icon(Icons.Filled.Warning, null, tint = Danger) },
            title = { Text("Accuse ${selected.label}?") },
            text = { Text("This is your only attempt. If you're wrong, the game is over and all data in this save file is erased.") },
            confirmButton = {
                TextButton(onClick = { confirming = false; onAccuse(selected) }) { Text("Accuse", color = Danger) }
            },
            dismissButton = { TextButton({ confirming = false }) { Text("Cancel") } },
        )
    }
}

@Composable
private fun OptionCard(option: Suspect, selected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Column(
        Modifier.clip(shape).background(Color.White)
            .border(if (selected) 3.dp else 1.dp, if (selected) PoliceBlue else Color(0xFFE0E0E0), shape)
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.fillMaxWidth().aspectRatio(1f).clip(RoundedCornerShape(10.dp))) {
            val image = rememberMediaImage(option.image)
            if (image != null) {
                Image(image, option.label, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            } else {
                // No picture in the media folders yet: colored tile with the initial.
                Box(
                    Modifier.fillMaxSize().background(Color(android.graphics.Color.parseColor(option.color))),
                    contentAlignment = Alignment.Center,
                ) { Text(option.label.take(1), color = Color.White, fontSize = 56.sp, fontWeight = FontWeight.Bold) }
            }
            if (selected) {
                Box(
                    Modifier.align(Alignment.TopEnd).padding(6.dp).size(28.dp).background(PoliceBlue, CircleShape),
                    contentAlignment = Alignment.Center,
                ) { Icon(Icons.Filled.Check, null, Modifier.size(18.dp), tint = Color.White) }
            }
        }
        Text(
            option.label, Modifier.padding(top = 8.dp, bottom = 2.dp), fontSize = 15.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium, textAlign = TextAlign.Center, maxLines = 2,
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
            .background(Brush.verticalGradient(if (solved) listOf(Color(0xFF0D1117), Color(0xFF2A2410)) else listOf(Color(0xFF0D1117), Color(0xFF3A0E0E))))
            .safeDrawingPadding()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(if (solved) "CASE SOLVED" else "GAME OVER", color = accent, fontSize = 40.sp, fontWeight = FontWeight.Black, letterSpacing = 4.sp, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Text(
            if (solved) "You found the truth. The case is closed."
            else "Wrong accusation. The culprit got away.\nYour save file has been erased.",
            color = Color.White.copy(alpha = 0.8f), fontSize = 17.sp, textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(48.dp))
        Button(onReturnToTitle, colors = ButtonDefaults.buttonColors(containerColor = accent)) {
            Text("Return to title", color = Color(0xFF0D1117), fontWeight = FontWeight.Bold)
        }
    }
}
