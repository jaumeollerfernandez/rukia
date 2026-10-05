package com.rukia.chat.infrastructure.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.MAX_NAME_LENGTH
import com.rukia.chat.domain.model.PlayerProfile

@Composable
fun ProfileScreen(
    profile: PlayerProfile,
    onPickAvatar: (Uri) -> Unit,
    onUpdate: (PlayerProfile) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    var editingName by remember { mutableStateOf(false) }
    // System photo picker: no storage permission needed.
    val picker = rememberLauncherForActivityResult(PickVisualMedia()) { uri -> uri?.let(onPickAvatar) }
    val pickImage = { picker.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }

    Column(Modifier.fillMaxSize().padding(contentPadding).padding(vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.clickable(onClick = pickImage)) {
            PlayerAvatar(profile, size = 140)
            Box(
                Modifier.align(Alignment.BottomEnd).size(44.dp)
                    .background(LocalPalette.current.primary, CircleShape),
                contentAlignment = Alignment.Center,
            ) { Icon(Icons.Filled.Edit, "Change avatar", tint = Color.White) }
        }
        Spacer(Modifier.height(24.dp))

        SettingRow("Name", onClick = { editingName = true }) {
            Text(profile.name, fontSize = 17.sp)
        }
        SettingRow("Dark mode", onClick = { onUpdate(profile.copy(darkMode = !profile.darkMode)) }) {
            Switch(profile.darkMode, onCheckedChange = { onUpdate(profile.copy(darkMode = it)) })
        }
    }

    if (editingName) {
        NameDialog(profile.name, onDismiss = { editingName = false }) {
            editingName = false
            onUpdate(profile.copy(name = it))
        }
    }
}

@Composable
private fun SettingRow(label: String, onClick: () -> Unit, trailing: @Composable () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 24.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, Modifier.weight(1f), color = LocalPalette.current.subText, fontSize = 15.sp)
        trailing()
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
