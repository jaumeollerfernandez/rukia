package com.rukia.game.infrastructure.persistence

import android.content.Context
import com.rukia.game.domain.model.CaseRecord
import com.rukia.game.domain.port.CaseRecords
import kotlinx.serialization.json.Json

/** Each case's record as JSON in its own settings file, away from files/cases/, which a replay wipes. */
class PrefsCaseRecords(context: Context) : CaseRecords {
    private val prefs = context.getSharedPreferences("records", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }

    override fun get(caseId: String): CaseRecord? =
        prefs.getString(caseId, null)?.let { runCatching { json.decodeFromString<CaseRecord>(it) }.getOrNull() }

    override fun save(caseId: String, record: CaseRecord) = prefs.edit().putString(caseId, json.encodeToString(record)).apply()
}
