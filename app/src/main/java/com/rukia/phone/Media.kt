package com.rukia.phone

import android.content.Context
import android.content.res.AssetManager
import android.media.MediaPlayer
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext

private const val MAX_IMAGE_SIZE = 512

/**
 * An image from the loaded case's media folder (e.g. "photos/rukia.jpg"), falling back to the shared media folder.
 * Null if [path] is null or the file is in neither.
 */
@Composable
fun rememberMediaImage(path: String?): ImageBitmap? {
    val assets = LocalContext.current.assets
    val caseMedia = "${CaseFolders.content(LocalCaseId.current)}/media"
    return remember(caseMedia, path) {
        path?.let { decodeMedia(assets, "$caseMedia/$it") ?: decodeMedia(assets, "${CaseFolders.SHARED_MEDIA}/$it") }
    }
}

/** Plays [path] (e.g. "audio/call.m4a") from the case's media, falling back to shared media; null if it's in neither. */
fun playMedia(context: Context, caseId: String, path: String, onDone: () -> Unit): MediaPlayer? {
    val fd = listOf("${CaseFolders.content(caseId)}/media/$path", "${CaseFolders.SHARED_MEDIA}/$path")
        .firstNotNullOfOrNull { runCatching { context.assets.openFd(it) }.getOrNull() } ?: return null
    return MediaPlayer().apply {
        fd.use { setDataSource(it.fileDescriptor, it.startOffset, it.length) }
        setOnCompletionListener { onDone() }
        prepare()
        start()
    }
}

// ponytail: decodes on the main thread, fine for a few avatar-sized photos; move off-thread if a screen shows many large ones.
private fun decodeMedia(assets: AssetManager, path: String) = runCatching {
    // Shrink big photos while decoding so a camera picture doesn't take tens of MB.
    val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    assets.open(path).use { BitmapFactory.decodeStream(it, null, bounds) }
    var sample = 1
    while (maxOf(bounds.outWidth, bounds.outHeight) / (sample * 2) >= MAX_IMAGE_SIZE) sample *= 2
    assets.open(path).use { BitmapFactory.decodeStream(it, null, BitmapFactory.Options().apply { inSampleSize = sample }) }
        ?.asImageBitmap()
}.getOrNull()
