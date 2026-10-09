package com.rukia.multimedia.infrastructure

import android.content.Context
import com.rukia.multimedia.application.ListPhotos
import com.rukia.multimedia.infrastructure.persistence.AssetGallery
import com.rukia.phone.infrastructure.CaseFolders

/** Composition root of the Multimedia app for one case. */
class MultimediaModule(context: Context, caseId: String) {
    private val gallery = AssetGallery { folder -> context.assets.list("${CaseFolders.content(caseId)}/$folder").orEmpty().toList() }

    val listPhotos = ListPhotos(gallery)
}
