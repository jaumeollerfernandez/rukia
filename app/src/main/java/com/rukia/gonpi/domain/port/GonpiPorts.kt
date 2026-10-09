package com.rukia.gonpi.domain.port

import com.rukia.gonpi.domain.model.Gonpi

/** The case's whole Gonpi, published or not. */
fun interface GonpiRepository {
    fun gonpi(): Gonpi
}

/** The posts the player has liked, by [com.rukia.gonpi.domain.model.Post.key], kept with the save. */
interface LikeRepository {
    fun all(): List<String>
    fun save(keys: List<String>)
}

/** The accounts in the phone's Contacts tab: the only ones search finds. The phone backs it with the chat app. */
fun interface ContactDirectory {
    fun contactIds(): Set<String>
}

/** Rings at [at] (epoch millis) to tell the player that [author] has just posted. */
fun interface PostAlarm {
    fun set(author: String, at: Long)
}
