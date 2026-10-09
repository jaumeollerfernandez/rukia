package com.rukia.multimedia.infrastructure.persistence

import com.rukia.multimedia.domain.port.Gallery

private val IMAGE = Regex(""".*\.(jpe?g|png|webp|gif|bmp)""", RegexOption.IGNORE_CASE)

/** Every image file in the case's multimedia/ folder. [list] lists a folder of the case's content. */
class AssetGallery(private val list: (folder: String) -> List<String>) : Gallery {
    override fun photos() = list(FOLDER).filter { IMAGE.matches(it) }.map { "$FOLDER/$it" }

    companion object { const val FOLDER = "multimedia" }
}
