package com.rukia.police.domain.port

import com.rukia.police.domain.model.CaseQuestion

interface CaseFileRepository {
    fun question(): CaseQuestion
}
