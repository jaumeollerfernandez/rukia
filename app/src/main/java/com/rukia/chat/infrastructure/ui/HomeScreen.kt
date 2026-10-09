package com.rukia.chat.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.Character
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.ui.RukiaIcons

enum class Tab(@StringRes val label: Int) { Chats(R.string.tab_chats), Calls(R.string.tab_calls), Contacts(R.string.tab_contacts), Profile(R.string.tab_profile) }

/** The tab's content above the iOS-style tab bar. [unreadChats] badges the Chats tab. */
@Composable
fun HomeScreen(tab: Tab, onTab: (Tab) -> Unit, owner: Character?, unreadChats: Int, content: @Composable (Tab) -> Unit) {
    val p = LocalPalette.current
    Column(Modifier.fillMaxSize().background(if (tab == Tab.Profile) p.grouped else p.background)) {
        Box(Modifier.weight(1f).statusBarsPadding()) { content(tab) }
        HorizontalDivider(thickness = 0.5.dp, color = p.separator)
        Row(Modifier.fillMaxWidth().background(p.bar).navigationBarsPadding().padding(horizontal = 8.dp, vertical = 8.dp)) {
            Tab.entries.forEach { t ->
                val selected = t == tab
                val color = if (selected) p.tint else p.subText
                Column(
                    Modifier.weight(1f).heightIn(min = 48.dp).selectable(selected, role = Role.Tab) { onTab(t) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                ) {
                    Box {
                        when (t) {
                            Tab.Chats -> Icon(if (selected) RukiaIcons.ChatFilled else RukiaIcons.Chat, null, Modifier.size(26.dp), tint = color)
                            Tab.Calls -> Icon(RukiaIcons.Phone, null, Modifier.size(26.dp), tint = color)
                            Tab.Contacts -> Icon(if (selected) RukiaIcons.PersonFilled else RukiaIcons.Person, null, Modifier.size(26.dp), tint = color)
                            // The phone owner's picture, ringed when the tab is selected.
                            Tab.Profile -> Box(if (selected) Modifier.border(2.dp, color, CircleShape).padding(2.dp) else Modifier) {
                                Avatar(owner, size = if (selected) 22 else 26)
                            }
                        }
                        if (t == Tab.Chats && unreadChats > 0) {
                            Box(
                                Modifier.align(Alignment.TopEnd).offset(10.dp, (-4).dp).defaultMinSize(18.dp, 18.dp)
                                    .background(Kit.Decline, RoundedCornerShape(9.dp)).padding(horizontal = 5.dp),
                                contentAlignment = Alignment.Center,
                            ) { Text("$unreadChats", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold) }
                        }
                    }
                    Text(stringResource(t.label), color = color, fontSize = 11.sp, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium)
                }
            }
        }
    }
}

@Composable
fun Placeholder(text: String) {
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(text, color = LocalPalette.current.subText, fontSize = 15.sp)
    }
}
