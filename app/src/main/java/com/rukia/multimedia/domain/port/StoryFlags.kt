package com.rukia.multimedia.domain.port

/** Tells the case's story the player saw something; the phone backs it with the chat app's story. */
fun interface StoryFlags {
    fun set(name: String)
}
