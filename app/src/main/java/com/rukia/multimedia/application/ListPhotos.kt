package com.rukia.multimedia.application

import com.rukia.multimedia.domain.model.Photo
import com.rukia.multimedia.domain.port.Gallery

/**
 * The camera roll in name order, which for camera files (IMG_0391, IMG_0393...) is the order they were taken.
 * Then the police photos saved from the records the player read ([mugshots]), in the order they came in.
 */
class ListPhotos(private val gallery: Gallery, private val mugshots: Gallery) {
    operator fun invoke(): List<Photo> = gallery.photos().sortedBy { it.path.substringAfterLast('/') } + mugshots.photos()
}
