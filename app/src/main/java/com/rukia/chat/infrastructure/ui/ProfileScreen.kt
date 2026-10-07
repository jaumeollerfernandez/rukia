package com.rukia.chat.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.phone.Kit

/** The phone owner's photo and name (fixed by the case: the player uses someone else's phone) and the app's settings. */
@Composable
fun ProfileScreen(owner: Character?, profile: PlayerProfile, onUpdate: (PlayerProfile) -> Unit) {
    val p = LocalPalette.current
    val name = owner?.name ?: stringResource(R.string.you)

    Column(Modifier.fillMaxSize().background(p.grouped).verticalScroll(rememberScrollState())) {
        LargeTitle(stringResource(R.string.tab_profile), Modifier.padding(bottom = 0.dp))
        Column(Modifier.fillMaxWidth().padding(top = 26.dp, bottom = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Avatar(owner, size = 132)
            Text(name, Modifier.padding(top = 10.dp), fontSize = 26.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.3).sp)
        }
        Column(Modifier.padding(horizontal = 16.dp).clip(RoundedCornerShape(16.dp)).background(p.card)) {
            SettingRow(Modifier) {
                Text(stringResource(R.string.name), Modifier.weight(1f), fontSize = 17.sp)
                Text(name, color = p.subText, fontSize = 17.sp)
            }
            HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = p.separator)
            SettingRow(Modifier.toggleable(profile.darkMode, role = Role.Switch) { onUpdate(profile.copy(darkMode = it)) }) {
                Text(stringResource(R.string.dark_mode), Modifier.weight(1f), fontSize = 17.sp)
                IosSwitch(profile.darkMode)
            }
        }
        Text(stringResource(R.string.dark_mode_note), Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp), color = p.subText, fontSize = 13.sp, lineHeight = 18.sp)
    }
}

@Composable
private fun SettingRow(modifier: Modifier, content: @Composable RowScope.() -> Unit) {
    Row(modifier.fillMaxWidth().heightIn(min = 52.dp).padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically, content = content)
}

/** The design's switch: green track when on, a white knob that slides. The row around it handles the toggle. */
@Composable
private fun IosSwitch(checked: Boolean) {
    val track by animateColorAsState(if (checked) Kit.Online else LocalPalette.current.switchOff, label = "track")
    val knob by animateDpAsState(if (checked) 20.dp else 0.dp, label = "knob")
    Box(Modifier.size(51.dp, 31.dp).background(track, RoundedCornerShape(16.dp)).padding(2.dp)) {
        Box(Modifier.offset(x = knob).size(27.dp).shadow(3.dp, CircleShape).background(Color.White, CircleShape))
    }
}
