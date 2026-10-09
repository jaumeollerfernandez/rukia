package com.rukia.gonpi.infrastructure

import android.content.Context
import com.rukia.gonpi.application.FindNewPost
import com.rukia.gonpi.application.GetGonpi
import com.rukia.gonpi.application.GetLikes
import com.rukia.gonpi.application.ScheduleNewPosts
import com.rukia.gonpi.application.SearchAccounts
import com.rukia.gonpi.application.ToggleLike
import com.rukia.gonpi.domain.model.Post
import com.rukia.gonpi.domain.port.ContactDirectory
import com.rukia.gonpi.domain.port.PostAlarm
import com.rukia.gonpi.infrastructure.notifications.AndroidPostAlarm
import com.rukia.gonpi.infrastructure.persistence.FileLikeRepository
import com.rukia.gonpi.infrastructure.persistence.JsonGonpiRepository
import com.rukia.phone.domain.model.caseTime
import com.rukia.phone.domain.model.isDebugCase
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.CaseFolders
import java.io.File
import java.time.ZoneId

/**
 * Composition root of the Gonpi app for one case: the only place that knows which adapters back the ports.
 * [contacts] is who search can find; the phone backs it with the chat app's contacts.
 */
class GonpiModule(context: Context, caseId: String, contacts: ContactDirectory = ContactDirectory { emptySet() }) {
    private val content = CaseFolders.content(caseId)
    private val repo = JsonGonpiRepository { path ->
        runCatching { context.assets.open("$content/$path") }.getOrNull()?.use { it.bufferedReader().readText() }
    }
    private val likes = FileLikeRepository(File(CaseFolders.saves(context, caseId, "gonpi"), "likes.txt"))
    private val clock = CaseClock.clock(caseId)
    private val timeOf = { spec: String -> caseTime(spec, CaseClock.start(context, caseId), ZoneId.systemDefault()) }
    // A debug case runs ahead of real time, so it never notifies: its posts go up while the tester watches.
    private val alarm = if (isDebugCase(caseId)) PostAlarm { _, _ -> } else AndroidPostAlarm(context, caseId)

    val getGonpi = GetGonpi(repo, timeOf, clock)
    val searchAccounts = SearchAccounts(contacts)
    val getLikes = GetLikes(likes)
    val toggleLike = ToggleLike(likes)
    val scheduleNewPosts = ScheduleNewPosts(repo, alarm, timeOf, clock)
    val findNewPost = FindNewPost(repo, timeOf)

    /** When [post] went up (epoch millis), for "2 hours ago". Null if it has no case time. */
    fun publishedAt(post: Post): Long? = post.at?.let(timeOf)

    /** The case's current time, epoch millis. */
    fun now(): Long = clock.millis()
}
