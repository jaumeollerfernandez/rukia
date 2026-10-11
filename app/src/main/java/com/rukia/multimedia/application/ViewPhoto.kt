package com.rukia.multimedia.application

import com.rukia.multimedia.domain.model.Photo
import com.rukia.multimedia.domain.port.StoryFlags

/**
 * The player opens a photo of the camera roll: the story learns it (`foto_img_0391` for multimedia/IMG_0391.jpg), so
 * the chats can ask about what's in it. Police photos don't count: their record already did.
 */
class ViewPhoto(private val flags: StoryFlags) {
    operator fun invoke(photo: Photo) {
        if (photo.plate == null) flags.set(photo.flag)
    }
}
