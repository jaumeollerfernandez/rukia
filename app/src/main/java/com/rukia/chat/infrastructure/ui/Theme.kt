package com.rukia.chat.infrastructure.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.PlayerProfile
import com.rukia.phone.rememberMediaImage

data class Palette(
    val bar: Color,
    val primary: Color,
    val background: Color,
    val wallpaper: Color,
    val inBubble: Color,
    val outBubble: Color,
    val subText: Color,
    val indicator: Color,
)

private val LightPalette = Palette(
    bar = Color(0xFF008069), primary = Color(0xFF008069), background = Color.White,
    wallpaper = Color(0xFFEFE7DE), inBubble = Color.White, outBubble = Color(0xFFD9FDD3),
    subText = Color(0xFF667781), indicator = Color(0xFFD9FDD3),
)

private val DarkPalette = Palette(
    bar = Color(0xFF1F2C34), primary = Color(0xFF00A884), background = Color(0xFF111B21),
    wallpaper = Color(0xFF0B141A), inBubble = Color(0xFF202C33), outBubble = Color(0xFF005C4B),
    subText = Color(0xFF8696A0), indicator = Color(0xFF103529),
)

val LocalPalette = staticCompositionLocalOf { LightPalette }

val Accent = Color(0xFF25D366)
val ReadTicks = Color(0xFF53BDEB)

@Composable
fun RukiaTheme(dark: Boolean, content: @Composable () -> Unit) {
    val p = if (dark) DarkPalette else LightPalette
    val scheme = if (dark) {
        darkColorScheme(primary = p.primary, onPrimary = p.background, background = p.background, surface = p.background)
    } else {
        lightColorScheme(primary = p.primary, background = p.background, surface = p.background)
    }
    CompositionLocalProvider(LocalPalette provides p) { MaterialTheme(colorScheme = scheme, content = content) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GreenBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit = {}) = TopAppBar(
    title = title,
    navigationIcon = navigationIcon,
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = LocalPalette.current.bar,
        titleContentColor = Color.White,
        navigationIconContentColor = Color.White,
    ),
)

@Composable
fun Avatar(c: Character?, size: Int, online: Boolean = false) {
    val photo = rememberMediaImage(c?.photo)
    Box {
        if (photo != null) {
            Image(photo, c?.name, Modifier.size(size.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        } else {
            Box(
                Modifier.size(size.dp).background(c?.colorValue() ?: Color.Gray, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(c?.name?.take(1).orEmpty(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = (size / 2.4).sp)
            }
        }
        if (online) {
            Box(
                Modifier.size(14.dp).align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.background, CircleShape).padding(2.dp)
                    .background(Accent, CircleShape)
            )
        }
    }
}

@Composable
fun PlayerAvatar(profile: PlayerProfile, size: Int, modifier: Modifier = Modifier) {
    // Avatars are stored pre-shrunk to 512px, so decoding on the main thread is cheap.
    val image = remember(profile.avatarPath) {
        profile.avatarPath?.let { BitmapFactory.decodeFile(it)?.asImageBitmap() }
    }
    val shape = modifier.size(size.dp).clip(CircleShape)
    if (image != null) {
        Image(image, "Your avatar", shape, contentScale = ContentScale.Crop)
    } else {
        Box(shape.background(Color(0xFFB0BEC5)), contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Person, "Your avatar", Modifier.size((size * 0.6).dp), tint = Color.White)
        }
    }
}

fun Character.colorValue() = Color(android.graphics.Color.parseColor(color))
