package com.rukia.multimedia.infrastructure

import android.content.Context
import com.rukia.multimedia.application.ListPhotos
import com.rukia.multimedia.domain.port.Gallery
import com.rukia.multimedia.infrastructure.persistence.AssetGallery
import com.rukia.phone.infrastructure.CaseFolders

/** Composition root of the Multimedia app for one case. [mugshots] are the police photos; the phone backs them with the Police app. */
class MultimediaModule(context: Context, caseId: String, mugshots: Gallery = Gallery { emptyList() }) {
    private val gallery = AssetGallery { folder -> context.assets.list("${CaseFolders.content(caseId)}/$folder").orEmpty().toList() }

    val listPhotos = ListPhotos(gallery, mugshots)
}
