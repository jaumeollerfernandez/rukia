package com.rukia.police.application

import com.rukia.police.domain.model.Spot
import com.rukia.police.domain.port.PlaceFinder

class FindPlace(private val finder: PlaceFinder) {
    operator fun invoke(query: String): Spot? = query.trim().takeIf { it.isNotEmpty() }?.let(finder::find)
}
