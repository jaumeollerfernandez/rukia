package com.rukia.game

/**
 * A playable case. Its content lives in assets/cases/<id>/ (characters.json, chats/, story/, police/case.json, media/)
 * and its progress in files/cases/<id>/, so cases never share chats or saves. Add a case here and give it a folder.
 */
class GameCase(val id: String, val number: Int, val title: String, val summary: String)

val cases = listOf(
    // Every feature in one place, to try things out: choices, calls with audio, delayed messages and notifications,
    // a group that appears a minute in, contacts and the police question.
    GameCase("test-case", 0, "Test Case", "Every feature of the phone in one place. Rukia calls, Jaume makes you wait, and a group appears after a minute."),
)