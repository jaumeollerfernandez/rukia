package com.rukia.game

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.Language
import com.rukia.phone.SystemBars

internal val Gold = Color(0xFFE0B354)

/** Dark backdrop shared by the menu, the case list and the options. */
internal fun Modifier.menuBackdrop() =
    fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF0D1117), Color(0xFF1B2433)))).safeDrawingPadding().padding(horizontal = 20.dp)

/** The first screen: KIMO and Start, Options, Exit. */
@Composable
fun MainMenu(onStart: () -> Unit, onOptions: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    val activity = LocalActivity.current
    Column(Modifier.menuBackdrop(), horizontalAlignment = Alignment.CenterHorizontally) {
        SignedInChip(Modifier.align(Alignment.End).padding(top = 12.dp))
        Spacer(Modifier.weight(1f))
        Text("KIMO", color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Black, letterSpacing = 12.sp)
        Spacer(Modifier.weight(1f))
        Column(Modifier.widthIn(max = 320.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            MenuButton(stringResource(R.string.menu_start), primary = true, onClick = onStart)
            MenuButton(stringResource(R.string.menu_options), onClick = onOptions)
            MenuButton(stringResource(R.string.menu_exit)) { activity?.finish() }
        }
        Spacer(Modifier.height(64.dp))
    }
}

@Composable
private fun MenuButton(label: String, primary: Boolean = false, onClick: () -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Box(
        Modifier.fillMaxWidth().heightIn(min = 54.dp).clip(shape)
            .background(if (primary) Gold else Color.White.copy(alpha = 0.06f))
            .border(1.dp, Gold.copy(alpha = if (primary) 1f else 0.4f), shape)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, color = if (primary) Color(0xFF0D1117) else Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
    }
}

/** Options: for now, only the interface language. Picking one restarts the screen in it. */
@Composable
fun OptionsScreen(onBack: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onBack)
    val context = LocalContext.current
    val activity = LocalActivity.current
    // Until the player picks one, the phone's language counts as picked if we have it.
    val current = Language.get(context) ?: Language.available.keys.firstOrNull { it.startsWith(context.resources.configuration.locales[0].language) }
    Column(Modifier.menuBackdrop()) {
        BackTitle(stringResource(R.string.options_title), onBack)
        Text(stringResource(R.string.options_language), Modifier.padding(top = 24.dp, bottom = 10.dp), color = Color.White.copy(alpha = 0.7f), fontSize = 15.sp)
        Column(Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White.copy(alpha = 0.06f))) {
            Language.available.forEach { (tag, name) ->
                Row(
                    Modifier.fillMaxWidth().heightIn(min = 54.dp)
                        .selectable(tag == current, role = Role.RadioButton) {
                            if (tag != current) {
                                Language.set(context, tag)
                                (activity as? Activity)?.recreate()
                            }
                        }
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(name, Modifier.weight(1f), color = Color.White, fontSize = 17.sp)
                    if (tag == current) Icon(Icons.Filled.Check, null, tint = Gold)
                }
            }
        }
    }
}

/** Back arrow and a title, at the top of the case list and the options. */
@Composable
internal fun BackTitle(title: String, onBack: () -> Unit) {
    Row(Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back),
            Modifier.clip(RoundedCornerShape(50)).clickable(role = Role.Button, onClick = onBack).padding(8.dp), tint = Color.White,
        )
        Text(title, Modifier.padding(start = 8.dp), color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
    }
}
