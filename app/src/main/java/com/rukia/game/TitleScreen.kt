package com.rukia.game

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.SystemBars

private val Gold = Color(0xFFE0B354)

/** Main window. Sign-in is faked as already done until Google Play Games is added. */
@Composable
fun TitleScreen(cases: List<GameCase>, onPlay: (GameCase) -> Unit) {
    SystemBars(lightBottomIcons = true)
    Column(
        Modifier.fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0D1117), Color(0xFF1B2433))))
            .safeDrawingPadding()
            .padding(horizontal = 20.dp),
    ) {
        SignedInChip(Modifier.align(Alignment.End).padding(top = 12.dp))
        Spacer(Modifier.height(48.dp))
        Text("1ife", color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Black, letterSpacing = 8.sp)
        Text("forgotten tales", color = Gold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 4.sp)
        Spacer(Modifier.height(48.dp))
        Text("Choose a case", color = Color.White.copy(alpha = 0.7f), fontSize = 15.sp)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(cases, key = { it.id }) { CaseCard(it) { onPlay(it) } }
        }
    }
}

@Composable
private fun SignedInChip(modifier: Modifier) {
    Row(
        modifier.background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(50)).padding(start = 6.dp, end = 14.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(Modifier.size(30.dp).background(Color(0xFF34A853), CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Person, null, Modifier.size(20.dp), tint = Color.White)
        }
        Column(Modifier.padding(start = 8.dp)) {
            Text("Detective", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text("Signed in · Google Play Games", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
        }
    }
}

@Composable
private fun CaseCard(case: GameCase, onClick: () -> Unit) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        Modifier.fillMaxWidth().clip(shape)
            .background(Color.White.copy(alpha = 0.06f))
            .border(1.dp, Gold.copy(alpha = 0.4f), shape)
            .clickable(onClick = onClick)
            .padding(18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(Modifier.weight(1f)) {
            Text("CASE %02d".format(case.number), color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Text(case.title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Text(case.summary, Modifier.padding(top = 4.dp), color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        }
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Play", tint = Gold)
    }
}
