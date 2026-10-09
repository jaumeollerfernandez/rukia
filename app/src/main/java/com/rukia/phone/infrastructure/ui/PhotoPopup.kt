package com.rukia.phone.infrastructure.ui

import com.rukia.phone.infrastructure.media.rememberMediaImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * Someone's profile photo up close, as WhatsApp or Instagram show it when you tap an avatar: a big square with the
 * name over the top edge. [photo] is a path inside the case's media; without the file, their initial on [color].
 * Tapping outside or back closes it.
 */
@Composable
fun PhotoPopup(photo: String?, name: String, color: Color, onDismiss: () -> Unit) {
    val image = rememberMediaImage(photo, maxSize = 1080)
    Dialog(onDismiss) {
        Box(Modifier.fillMaxWidth(0.86f).aspectRatio(1f).background(color)) {
            if (image != null) Image(image, name, Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            else Text(name.take(1).uppercase(), Modifier.align(Alignment.Center), color = Color.White, fontSize = 120.sp, fontWeight = FontWeight.SemiBold)
            Text(
                name,
                Modifier.fillMaxWidth().background(Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.55f), Color.Transparent)))
                    .padding(start = 14.dp, end = 14.dp, top = 10.dp, bottom = 18.dp),
                color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
