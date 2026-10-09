package com.rukia.game.domain.model

/** How a case shows in the list: a real case file, a training one, or a debug simulator. */
enum class CaseKind { Case, Training, Debug }

/**
 * A playable case. Its content lives in assets/cases/<id>/ (characters.json, chats/, story/, police/case.json, media/)
 * and its progress in files/cases/<id>/, so cases never share chats or saves. The cases themselves are listed in [cases].
 * [headline], [stamp] and [facts] fill the case file card; like the rest of a case's content, they aren't translated.
 */
class GameCase(
    val id: String,
    val number: Int,
    val title: String,
    val summary: String,
    val kind: CaseKind = CaseKind.Case,
    val headline: String = title,
    val stamp: String? = null,
    val facts: List<Pair<String, String>> = emptyList(),
)

