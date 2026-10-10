package com.rukia.multimedia.domain.port

import com.rukia.multimedia.domain.model.Photo

/** Photos on the phone's camera roll, by their paths inside the case's content folder ("multimedia/IMG_0391.jpg"). */
fun interface Gallery {
    fun photos(): List<Photo>
}
