package com.rukia.multimedia.domain.port

/** The photos on the phone's camera roll, as paths inside the case's content folder ("multimedia/IMG_0391.jpg"). */
fun interface Gallery {
    fun photos(): List<String>
}
