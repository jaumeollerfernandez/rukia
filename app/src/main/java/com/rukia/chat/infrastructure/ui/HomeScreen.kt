package com.rukia.chat.infrastructure.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.PlayerProfile

enum class Tab(val label: String, val icon: ImageVector) {
    Chats("Chats", Icons.Filled.Email),
    Calls("Calls", Icons.Filled.Call),
    Contacts("Contacts", Icons.Filled.AccountBox),
    Profile("Profile", Icons.Filled.AccountCircle),
}

private val BarHeight = 64.dp
private val BarMargin = 12.dp

/**
 * Top bar plus the tab's content, with the tab bar floating over the content like Telegram's. [content] gets the
 * padding its list needs at the bottom so the last item can scroll out from under the bar.
 */
@Composable
fun HomeScreen(tab: Tab, onTab: (Tab) -> Unit, profile: PlayerProfile, content: @Composable (Tab, PaddingValues) -> Unit) {
    Scaffold(
        topBar = { GreenBar(title = { Text(tab.label, fontWeight = FontWeight.Bold) }) },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0), // content runs to the bottom edge, under the floating bar
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            val navBar = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            content(tab, PaddingValues(bottom = BarHeight + BarMargin * 2 + navBar))
            FloatingTabBar(tab, onTab, profile, Modifier.align(Alignment.BottomCenter))
        }
    }
}

@Composable
private fun FloatingTabBar(selected: Tab, onTab: (Tab) -> Unit, profile: PlayerProfile, modifier: Modifier) {
    val shape = RoundedCornerShape(BarHeight / 2)
    Row(
        modifier.navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = BarMargin)
            .fillMaxWidth().height(BarHeight)
            .shadow(12.dp, shape)
            .background(LocalPalette.current.inBubble.copy(alpha = 0.97f), shape)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Tab.entries.forEach { tab ->
            val isSelected = tab == selected
            val background by animateColorAsState(if (isSelected) LocalPalette.current.indicator else Color.Transparent, label = "tab")
            val tint = if (isSelected) LocalPalette.current.primary else LocalPalette.current.subText
            Column(
                Modifier.weight(1f).fillMaxHeight()
                    .clip(RoundedCornerShape(BarHeight / 2))
                    .background(background)
                    .clickable { onTab(tab) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (tab == Tab.Profile && profile.avatarPath != null) {
                    // Your own picture, ringed when the tab is selected.
                    PlayerAvatar(
                        profile, size = 24,
                        modifier = if (isSelected) Modifier.border(2.dp, tint, CircleShape).padding(2.dp) else Modifier,
                    )
                } else {
                    Icon(tab.icon, null, Modifier.size(22.dp), tint = tint)
                }
                Text(
                    tab.label, color = tint, fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
fun Placeholder(text: String) {
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(text, color = LocalPalette.current.subText)
    }
}
