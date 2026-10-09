package com.rukia.multimedia.application

import com.rukia.multimedia.domain.port.Gallery

/** The camera roll in name order, which for camera files (IMG_0391, IMG_0393...) is the order they were taken. */
class ListPhotos(private val gallery: Gallery) {
    operator fun invoke(): List<String> = gallery.photos().sortedBy { it.substringAfterLast('/') }
}
