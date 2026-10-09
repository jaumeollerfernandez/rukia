package com.rukia.gonpi.domain.model

import kotlinx.serialization.Serializable
import java.text.Normalizer

/** A Gonpi user: a contact or anyone else. [photo] is a path inside the case's media folder; without it the avatar shows the initial in [color]. */
@Serializable
data class Account(
    val id: String,
    val username: String,
    val name: String = "",
    val photo: String? = null,
    val bio: String = "",
    val color: String = "#888888",
    val followers: Int = 0,
    val following: Int = 0,
)

/** [at]: case time ("D3 23:10") the comment appears; without it, it's there from the start. */
@Serializable
data class Comment(val author: String, val text: String, val at: String? = null)

/**
 * [image] is a path inside the case's media folder. [at]: case time ("D2 18:40", or "D-12 19:30" for the phone's past)
 * the post is published, shown as "2 hours ago"; without it, it's there from the start and shows [time] as written.
 */
@Serializable
data class Post(
    val author: String,
    val image: String,
    val caption: String = "",
    val time: String = "",
    val likes: Int = 0,
    val comments: List<Comment> = emptyList(),
    val at: String? = null,
    /** An ad: shows «Sponsored» under the author's name. */
    val sponsored: Boolean = false,
) {
    /** Identifies the post in the player's saved likes, so reordering gonpi.json keeps them. */
    val key get() = "$author:$image:${caption.hashCode()}"
}

/** Everything on Gonpi in one case. Posts show in the feed in file order, so put the newest first. */
@Serializable
data class Gonpi(val accounts: List<Account> = emptyList(), val posts: List<Post> = emptyList()) {
    init {
        val ids = accounts.map { it.id }.toSet()
        require(ids.size == accounts.size) { "Account ids must be unique" }
        val authors = posts.map { it.author } + posts.flatMap { p -> p.comments.map { it.author } }
        authors.firstOrNull { it !in ids }?.let { error("'$it' posts or comments but has no account") }
    }

    fun account(id: String) = accounts.first { it.id == id }

    fun postsBy(id: String) = posts.filter { it.author == id }

    /** The accounts among [ids] whose username or name contains [query], ignoring case and accents. None for a blank query. */
    fun search(query: String, ids: Set<String>): List<Account> {
        val q = query.trim().plain()
        if (q.isEmpty()) return emptyList()
        return accounts.filter { it.id in ids && (q in it.username.plain() || q in it.name.plain()) }
    }

    private fun String.plain() = Normalizer.normalize(this, Normalizer.Form.NFD).replace(Regex("\\p{M}"), "").lowercase()

    /**
     * Gonpi as it is at [now]: posts and comments whose [Post.at] / [Comment.at] time, read by [timeOf], hasn't come yet
     * are left out. Posts with a time come newest first; those without one follow, in file order.
     */
    fun publishedBy(now: Long, timeOf: (String) -> Long?): Gonpi {
        fun published(at: String?) = at == null || (timeOf(at) ?: 0) <= now
        return copy(posts = posts.filter { published(it.at) }.map { p -> p.copy(comments = p.comments.filter { published(it.at) }) }
            .sortedByDescending { p -> p.at?.let(timeOf) ?: Long.MIN_VALUE })
    }
}
