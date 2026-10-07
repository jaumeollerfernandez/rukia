package com.rukia.gonpi

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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
 * [image] is a path inside the case's media folder. [time] is shown as written ("2h", "3 days ago").
 * [at]: case time ("D2 18:40") the post is published; without it, it's there from the start.
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

    /** Gonpi as it is at [now]: posts and comments whose [Post.at] / [Comment.at] time, read by [timeOf], hasn't come yet are left out. */
    fun publishedBy(now: Long, timeOf: (String) -> Long?): Gonpi {
        fun published(at: String?) = at == null || (timeOf(at) ?: 0) <= now
        return copy(posts = posts.filter { published(it.at) }.map { p -> p.copy(comments = p.comments.filter { published(it.at) }) })
    }

    companion object {
        const val PATH = "gonpi/gonpi.json"
        private val json = Json { ignoreUnknownKeys = true }
        fun parse(text: String) = json.decodeFromString<Gonpi>(text)
    }
}
