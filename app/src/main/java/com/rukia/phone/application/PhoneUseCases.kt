package com.rukia.phone.application

import com.rukia.phone.domain.model.HomeLayout
import com.rukia.phone.domain.model.nextEvent
import com.rukia.phone.domain.port.CaseCalendar
import com.rukia.phone.domain.port.HomeLayoutRepository
import com.rukia.phone.domain.port.StoryTimeline

/** Where each of [appIds] sits on the home screen: where the player left it, or the first free cell. */
class GetHomeLayout(private val repo: HomeLayoutRepository) {
    operator fun invoke(appIds: List<String>): Map<String, Int> = HomeLayout.arrange(appIds, repo.load())
}

/** Moves [app] to cell [to] (swapping with whatever was there) and remembers it. Returns the new layout. */
class MoveApp(private val repo: HomeLayoutRepository) {
    operator fun invoke(cells: Map<String, Int>, app: String, to: Int): Map<String, Int> =
        HomeLayout.move(cells, app, to).also(repo::save)
}

/** When the story next has something to do, after now. Null if it's waiting for the player. */
class NextStoryEvent(private val calendar: CaseCalendar, private val story: StoryTimeline) {
    operator fun invoke(): Long? = nextEvent(story.chats(), calendar.now())
}

/**
 * Moves a debug case [millis] ahead. The clock stops at every story event on the way, so each timed line arrives and
 * each choice expires in order, exactly as if the time had really passed.
 */
class SkipTime(private val calendar: CaseCalendar, private val story: StoryTimeline) {
    operator fun invoke(millis: Long) {
        val target = calendar.now() + millis
        // ponytail: capped steps, in case a story loops on itself; a whole week is a few hundred events.
        for (step in 0 until 2_000) {
            story.advance()
            val now = calendar.now()
            val next = nextEvent(story.chats(), now)?.takeIf { it <= target } ?: break
            calendar.skip(next - now)
        }
        calendar.skip(target - calendar.now())
        story.advance()
    }
}
