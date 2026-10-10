package com.rukia.multimedia.domain.model

/** A photo at [path] in the case's content. A police photo has the [plate] it was taken with, front or [profile]. */
data class Photo(val path: String, val plate: String? = null, val profile: Boolean = false)
