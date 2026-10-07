package com.rukia.chat.infrastructure.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.MAX_NAME_LENGTH
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.phone.Kit
import com.rukia.phone.RukiaIcons

@Composable
fun ProfileScreen(profile: PlayerProfile, onPickAvatar: (Uri) -> Unit, onUpdate: (PlayerProfile) -> Unit) {
    val p = LocalPalette.current
    var editingName by remember { mutableStateOf(false) }
    // System photo picker: no storage permission needed.
    val picker = rememberLauncherForActivityResult(PickVisualMedia()) { uri -> uri?.let(onPickAvatar) }

    Column(Modifier.fillMaxSize().background(p.grouped).verticalScroll(rememberScrollState())) {
        LargeTitle("Profile", Modifier.padding(bottom = 0.dp))
        Column(Modifier.fillMaxWidth().padding(top = 26.dp, bottom = 28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.clickable { picker.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
                    .semantics { contentDescription = "Change your photo" },
            ) {
                PlayerAvatar(profile, size = 132)
                Box(
                    Modifier.align(Alignment.BottomEnd).padding(2.dp).size(40.dp).border(3.dp, p.grouped, CircleShape)
                        .padding(3.dp).background(Kit.Tint, CircleShape),
                    contentAlignment = Alignment.Center,
                ) { Icon(RukiaIcons.Camera, null, Modifier.size(18.dp), tint = Color.White) }
            }
            Text(profile.name, Modifier.padding(top = 10.dp), fontSize = 26.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.3).sp)
            Text("Your photo and name show in every chat", Modifier.padding(top = 6.dp), color = p.subText, fontSize = 15.sp)
        }
        Column(Modifier.padding(horizontal = 16.dp).clip(RoundedCornerShape(16.dp)).background(p.card)) {
            SettingRow(Modifier.clickable { editingName = true }) {
                Text("Name", Modifier.weight(1f), fontSize = 17.sp)
                Text(profile.name, color = p.subText, fontSize = 17.sp)
            }
            HorizontalDivider(Modifier.padding(start = 16.dp), thickness = 0.5.dp, color = p.separator)
            SettingRow(Modifier.toggleable(profile.darkMode, role = Role.Switch) { onUpdate(profile.copy(darkMode = it)) }) {
                Text("Dark mode", Modifier.weight(1f), fontSize = 17.sp)
                IosSwitch(profile.darkMode)
            }
        }
        Text("Dark mode changes the Chats app only.", Modifier.padding(start = 32.dp, end = 32.dp, top = 8.dp), color = p.subText, fontSize = 13.sp, lineHeight = 18.sp)
    }

    if (editingName) {
        NameDialog(profile.name, onDismiss = { editingName = false }) {
            editingName = false
            onUpdate(profile.copy(name = it))
        }
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

@Composable
private fun NameDialog(current: String, onDismiss: () -> Unit, onSave: (String) -> Unit) {
    var name by remember { mutableStateOf(current) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Your name") },
        text = {
            OutlinedTextField(
                name, { if (it.length <= MAX_NAME_LENGTH) name = it },
                singleLine = true,
                supportingText = { Text("${name.length}/$MAX_NAME_LENGTH") },
            )
        },
        confirmButton = { TextButton(enabled = name.isNotBlank(), onClick = { onSave(name) }) { Text("Save") } },
        dismissButton = { TextButton(onDismiss) { Text("Cancel") } },
    )
}
