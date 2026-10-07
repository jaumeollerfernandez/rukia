package com.rukia.phone

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf
import java.io.File

/**
 * Where each case keeps its things. Content ships in assets/cases/<caseId>/ (characters, chats, story, police,
 * media); progress is saved in files/cases/<caseId>/<app>/. Media every case uses goes in assets/shared/media/.
 */
object CaseFolders {
    const val SHARED_MEDIA = "shared/media"

    /** A debug case plays the content of the case it copies. */
    fun content(caseId: String) = "cases/${caseId.removePrefix(DEBUG_CASE_PREFIX)}"

    fun saves(context: Context, caseId: String, app: String) =
        File(context.filesDir, "cases/$caseId/$app").apply { mkdirs() }
}

/** Id of the case loaded on the phone. Set by [PhoneScreen]. */
val LocalCaseId = staticCompositionLocalOf<String> { error("No case loaded") }
