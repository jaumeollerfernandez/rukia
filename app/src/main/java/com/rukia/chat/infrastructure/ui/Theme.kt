package com.rukia.chat.infrastructure.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.Character
import com.rukia.phone.Kit
import com.rukia.phone.RukiaIcons
import com.rukia.phone.rememberMediaImage

/** Colors of the Chats app, from the design canvas's light and dark artboards. */
data class Palette(
    val background: Color,
    /** Grouped screens (Profile) sit cards on this. */
    val grouped: Color,
    val card: Color,
    val section: Color,
    val text: Color,
    val subText: Color,
    val preview: Color,
    val separator: Color,
    val bar: Color,
    val tint: Color,
    val red: Color,
    val inBubble: Color,
    val choiceBackground: Color,
    val choiceBorder: Color,
    val callBackground: Color,
    val callText: Color,
    val missedBackground: Color,
    val missedText: Color,
    val switchOff: Color,
)

private val LightPalette = Palette(
    background = Color.White, grouped = Color(0xFFF2F2F7), card = Color.White, section = Color(0xFFF6F6F9),
    text = Color(0xFF111114), subText = Color(0xFF6C6C70), preview = Color(0xFF3A3A3F), separator = Color(0xFFE2E2E7),
    bar = Color(0xEBF9F9FB), tint = Kit.Tint, red = Color(0xFFC62A20), inBubble = Color(0xFFE9E9EE),
    choiceBackground = Color.White, choiceBorder = Color(0xFFD6D6DD), callBackground = Color(0xFFE8F6EC), callText = Color(0xFF17703A),
    missedBackground = Color(0xFFFDECEA), missedText = Color(0xFFA1251B), switchOff = Color(0xFFD6D6DC),
)

private val DarkPalette = Palette(
    background = Color.Black, grouped = Color.Black, card = Color(0xFF1C1C1E), section = Color(0xFF111113),
    text = Color(0xFFF2F2F7), subText = Color(0xFFA1A1A8), preview = Color(0xFFC4C4CA), separator = Color(0xFF38383C),
    bar = Color(0xEB161618), tint = Color(0xFF6E9BFF), red = Color(0xFFFF6B60), inBubble = Color(0xFF26262A),
    choiceBackground = Color(0xFF1C1C1E), choiceBorder = Color(0xFF3A3A3F), callBackground = Color(0xFF123222), callText = Color(0xFF5FD88A),
    missedBackground = Color(0xFF3A1715), missedText = Color(0xFFFF9A90), switchOff = Color(0xFF39393D),
)

val LocalPalette = staticCompositionLocalOf { LightPalette }

@Composable
fun RukiaTheme(dark: Boolean, content: @Composable () -> Unit) {
    val p = if (dark) DarkPalette else LightPalette
    val scheme = (if (dark) darkColorScheme() else lightColorScheme()).copy(
        primary = p.tint, background = p.background, surface = p.background, onBackground = p.text, onSurface = p.text,
    )
    CompositionLocalProvider(LocalPalette provides p) {
        MaterialTheme(colorScheme = scheme) { CompositionLocalProvider(LocalContentColor provides p.text, content = content) }
    }
}

/** iOS-style large screen title. */
@Composable
fun LargeTitle(text: String, modifier: Modifier = Modifier) {
    Text(text, modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 10.dp), fontSize = 34.sp, lineHeight = 40.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.6).sp)
}

/** A character's photo, or their initial on their color. [online] adds the green dot, ringed in [ring]. */
@Composable
fun Avatar(c: Character?, size: Int, online: Boolean = false, ring: Color = LocalPalette.current.background) {
    val photo = rememberMediaImage(c?.photo)
    Box {
        if (photo != null) {
            Image(photo, c?.name, Modifier.size(size.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        } else {
            Box(Modifier.size(size.dp).background(c?.colorValue() ?: Color.Gray, CircleShape), contentAlignment = Alignment.Center) {
                Text(c?.name?.take(1).orEmpty(), color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = (size / 2.4).sp)
            }
        }
        if (online) {
            val dot = (size / 4.2f).dp
            Box(Modifier.align(Alignment.BottomEnd).padding(1.dp).size(dot).border(2.dp, ring, CircleShape).padding(2.dp).background(Kit.Online, CircleShape))
        }
    }
}

/** Two overlapping avatars for a group, like the design's chat list. */
@Composable
fun GroupAvatar(first: Character?, second: Character?, size: Int) {
    val small = (size * 2) / 3
    val ring = LocalPalette.current.background
    Box(Modifier.size(size.dp)) {
        Box(Modifier.align(Alignment.TopStart)) { Avatar(first, small) }
        Box(Modifier.align(Alignment.BottomEnd).border(2.5.dp, ring, CircleShape).padding(2.dp)) { Avatar(second, small - 4) }
    }
}

fun Character.colorValue() = Color(android.graphics.Color.parseColor(color))
