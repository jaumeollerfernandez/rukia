package com.rukia.chat.application

import com.rukia.chat.domain.model.REFRESH_CHOICE
import com.rukia.chat.domain.port.ChatRepository
import com.rukia.chat.domain.port.MessageNotifier
import com.rukia.chat.domain.port.StoryEngine
import java.time.Clock

/**
 * Something happened outside the chats that the story can react to: `ficha_dani` once the player read Dani's record,
 * `foto_img_0391` once they opened that photo. Chats waiting in their hub of questions take [REFRESH_CHOICE], so the
 * questions it opens show at once instead of after midnight. Nothing happens if the story doesn't declare it or knew it.
 */
class SetStoryFlag(
    private val story: StoryEngine,
    private val chats: ChatRepository,
    private val notifier: MessageNotifier,
    private val clock: Clock = Clock.systemDefaultZone(),
) {
    operator fun invoke(name: String): Unit = synchronized(story) {
        if (story.variable(name) != false) return
        story.setVariable(name, true)
        for (chat in chats.all()) {
            val refresh = chat.choices.indexOf(REFRESH_CHOICE).takeIf { it >= 0 } ?: continue
            continueChat(chat.copy(choices = emptyList()), story.choose(chat.id, refresh), clock, chats, notifier)
        }
    }
}
