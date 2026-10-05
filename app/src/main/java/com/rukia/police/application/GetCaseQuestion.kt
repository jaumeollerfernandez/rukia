package com.rukia.police.application

import com.rukia.police.domain.port.CaseFileRepository

class GetCaseQuestion(private val caseFile: CaseFileRepository) {
    operator fun invoke() = caseFile.question()
}
