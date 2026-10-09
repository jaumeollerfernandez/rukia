package com.rukia.gonpi.application

import com.rukia.gonpi.domain.model.Account
import com.rukia.gonpi.domain.model.Gonpi
import com.rukia.gonpi.domain.model.Post
import com.rukia.gonpi.domain.port.ContactDirectory
import com.rukia.gonpi.domain.port.GonpiRepository
import com.rukia.gonpi.domain.port.LikeRepository
import com.rukia.gonpi.domain.port.PostAlarm
import java.time.Clock

/** Gonpi as it is now in the case's week. [timeOf] turns a case time ("D3 21:00") into epoch millis. */
class GetGonpi(private val repo: GonpiRepository, private val timeOf: (String) -> Long?, private val clock: Clock) {
    operator fun invoke(): Gonpi = repo.gonpi().publishedBy(clock.millis(), timeOf)
}

/** Search only finds the phone's contacts, by username or name. */
class SearchAccounts(private val contacts: ContactDirectory) {
    operator fun invoke(gonpi: Gonpi, query: String): List<Account> = gonpi.search(query, contacts.contactIds())
}

class GetLikes(private val likes: LikeRepository) {
    operator fun invoke(): List<String> = likes.all()
}

/** Likes [post], or unlikes it if it was liked. Returns the likes after the change. */
class ToggleLike(private val likes: LikeRepository) {
    operator fun invoke(post: Post): List<String> {
        val all = likes.all()
        val next = if (post.key in all) all - post.key else all + post.key
        likes.save(next)
        return next
    }
}

/** Sets an alarm for every post still to come, so the player hears of it even with the game closed. */
class ScheduleNewPosts(
    private val repo: GonpiRepository,
    private val alarm: PostAlarm,
    private val timeOf: (String) -> Long?,
    private val clock: Clock,
) {
    operator fun invoke() {
        val now = clock.millis()
        for (post in repo.gonpi().posts) post.at?.let(timeOf)?.takeIf { it > now }?.let { alarm.set(post.author, it) }
    }
}

/**
 * The author of a post that goes up at [at], if there still is one: an alarm left over from before a reset (the
 * week starts over, so every time moves) finds none.
 */
class FindNewPost(private val repo: GonpiRepository, private val timeOf: (String) -> Long?) {
    operator fun invoke(author: String, at: Long): Account? {
        val gonpi = repo.gonpi()
        return gonpi.account(author).takeIf { gonpi.postsBy(author).any { p -> p.at?.let(timeOf) == at } }
    }
}
