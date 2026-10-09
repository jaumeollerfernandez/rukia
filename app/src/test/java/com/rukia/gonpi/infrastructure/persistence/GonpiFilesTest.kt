package com.rukia.gonpi.infrastructure.persistence

import com.rukia.gonpi.domain.model.Gonpi
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GonpiFilesTest {
    @Test fun `every case's gonpi file parses and its images exist`() {
        for (case in File("src/main/assets/cases").listFiles()!!) {
            val file = File(case, JsonGonpiRepository.PATH).takeIf { it.exists() } ?: continue
            val gonpi = JsonGonpiRepository.parse(file.readText())
            for (path in gonpi.posts.map { it.image } + gonpi.accounts.mapNotNull { it.photo }) {
                assertTrue(File(case, "media/$path").exists() || File("src/main/assets/shared/media/$path").exists(), "${case.name}: missing media/$path")
            }
        }
    }

    @Test fun `posts and comments with a time only show once it has come`() {
        val gonpi = JsonGonpiRepository.parse("""{ "accounts": [{ "id": "a", "username": "a" }], "posts": [
            { "author": "a", "image": "new.jpg", "at": "D3 10:00" },
            { "author": "a", "image": "old.jpg", "comments": [{ "author": "a", "text": "now" }, { "author": "a", "text": "later", "at": "D2 09:00" }] }
        ] }""")
        val times = mapOf("D3 10:00" to 300L, "D2 09:00" to 200L)
        val early = gonpi.publishedBy(now = 100) { times[it] }
        assertEquals(listOf("old.jpg"), early.posts.map { it.image })
        assertEquals(listOf("now"), early.posts.single().comments.map { it.text })
        assertEquals(2, gonpi.publishedBy(now = 300) { times[it] }.posts.size)
    }

    @Test fun `search finds contacts by username or name, without accents, and nobody else`() {
        val gonpi = JsonGonpiRepository.parse("""{ "accounts": [
            { "id": "eric", "username": "eric.forner", "name": "Èric" },
            { "id": "nuria", "username": "nuriii.g", "name": "Núria" },
            { "id": "guia", "username": "ignasi.coll", "name": "Ignasi" }
        ] }""")
        val contacts = setOf("eric", "nuria")
        assertEquals(listOf("eric"), gonpi.search("ÈRIC", contacts).map { it.id })
        assertEquals(listOf("nuria"), gonpi.search(" nuri ", contacts).map { it.id })
        assertEquals(emptyList(), gonpi.search("ignasi", contacts), "not a contact")
        assertEquals(emptyList(), gonpi.search("  ", contacts))
    }

    @Test fun `a post by someone without an account is rejected`() {
        assertFailsWith<IllegalStateException> { JsonGonpiRepository.parse("""{ "posts": [{ "author": "ghost", "image": "a.jpg" }] }""") }
    }
}
