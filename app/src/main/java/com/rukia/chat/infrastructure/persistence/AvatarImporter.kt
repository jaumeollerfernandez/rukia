package com.rukia.chat.infrastructure.persistence

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import java.io.File

/** Copies a picked image into app storage, downscaled, so it survives the picker's URI expiring. */
class AvatarImporter(private val resolver: ContentResolver, saveRoot: File) {
    private val dir = File(saveRoot, "avatars").apply { mkdirs() }

    /** Returns the new file path, or null if the image can't be decoded. Blocking: call off the main thread. */
    fun import(uri: Uri): String? = runCatching {
        // ImageDecoder applies EXIF rotation, so phone photos aren't sideways.
        val bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(resolver, uri)) { decoder, info, _ ->
            val scale = MAX_SIZE.toFloat() / maxOf(info.size.width, info.size.height)
            if (scale < 1) decoder.setTargetSize((info.size.width * scale).toInt(), (info.size.height * scale).toInt())
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
        }
        // New name each time so the UI notices the change; old files are cleaned up.
        val file = File(dir, "avatar-${System.currentTimeMillis()}.jpg")
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it) }
        dir.listFiles().orEmpty().filter { it != file }.forEach(File::delete)
        file.absolutePath
    }.getOrNull()

    private companion object { const val MAX_SIZE = 512 }
}
