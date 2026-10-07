package com.rukia.gonpi

import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GonpiTest {
    @Test fun `every case's gonpi file parses and its images exist`() {
        for (case in File("src/main/assets/cases").listFiles()!!) {
            val file = File(case, Gonpi.PATH).takeIf { it.exists() } ?: continue
            val gonpi = Gonpi.parse(file.readText())
            for (path in gonpi.posts.map { it.image } + gonpi.accounts.mapNotNull { it.photo }) {
                assertTrue(File(case, "media/$path").exists() || File("src/main/assets/shared/media/$path").exists(), "${case.name}: missing media/$path")
            }
        }
    }

    @Test fun `posts and comments with a time only show once it has come`() {
        val gonpi = Gonpi.parse("""{ "accounts": [{ "id": "a", "username": "a" }], "posts": [
            { "author": "a", "image": "new.jpg", "at": "D3 10:00" },
            { "author": "a", "image": "old.jpg", "comments": [{ "author": "a", "text": "now" }, { "author": "a", "text": "later", "at": "D2 09:00" }] }
        ] }""")
        val times = mapOf("D3 10:00" to 300L, "D2 09:00" to 200L)
        val early = gonpi.publishedBy(now = 100) { times[it] }
        assertEquals(listOf("old.jpg"), early.posts.map { it.image })
        assertEquals(listOf("now"), early.posts.single().comments.map { it.text })
        assertEquals(2, gonpi.publishedBy(now = 300) { times[it] }.posts.size)
    }

    @Test fun `a post by someone without an account is rejected`() {
        assertFailsWith<IllegalStateException> { Gonpi.parse("""{ "posts": [{ "author": "ghost", "image": "a.jpg" }] }""") }
    }
}
