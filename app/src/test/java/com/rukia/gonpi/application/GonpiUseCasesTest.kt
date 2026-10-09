package com.rukia.gonpi.application

import com.rukia.gonpi.domain.model.Account
import com.rukia.gonpi.domain.model.Gonpi
import com.rukia.gonpi.domain.model.Post
import com.rukia.gonpi.domain.port.GonpiRepository
import com.rukia.gonpi.domain.port.LikeRepository
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GonpiUseCasesTest {
    private val gonpi = Gonpi(
        listOf(Account("ana", "ana.a"), Account("bo", "bo.b")),
        listOf(Post("ana", "old.jpg", at = "past"), Post("bo", "soon.jpg", at = "soon")),
    )
    private val repo = GonpiRepository { gonpi }
    private val times = mapOf("past" to 50L, "soon" to 200L)
    private val clock = Clock.fixed(Instant.ofEpochMilli(100), ZoneOffset.UTC)

    @Test fun `only posts still to come get an alarm, and only a post that is still there notifies`() {
        val alarms = mutableListOf<Pair<String, Long>>()
        ScheduleNewPosts(repo, { author, at -> alarms += author to at }, times::get, clock)()
        assertEquals(listOf("bo" to 200L), alarms)
        assertEquals("bo", FindNewPost(repo, times::get)("bo", 200)?.id)
        assertNull(FindNewPost(repo, times::get)("bo", 999), "an alarm from before a reset finds no post")
    }

    @Test fun `a like toggles and is saved`() {
        val saved = object : LikeRepository {
            var keys = emptyList<String>()
            override fun all() = keys
            override fun save(keys: List<String>) { this.keys = keys }
        }
        val post = gonpi.posts.first()
        assertEquals(listOf(post.key), ToggleLike(saved)(post))
        assertEquals(emptyList(), ToggleLike(saved)(post))
        assertEquals(emptyList(), saved.keys)
    }
}
