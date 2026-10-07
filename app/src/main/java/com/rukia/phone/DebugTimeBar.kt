package com.rukia.phone

import androidx.compose.ui.res.stringResource
import com.rukia.R
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.clickable
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.infrastructure.ChatModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val MINUTE = 60_000L
private const val HOUR = 60 * MINUTE

/** Top bar of a debug case: shows the case time and moves it ahead, to try the story's days without waiting for them. */
@Composable
fun DebugTimeBar(caseId: String) {
    val context = LocalContext.current.applicationContext
    val scope = rememberCoroutineScope()
    var busy by remember { mutableStateOf(false) }
    val now by produceState(CaseClock.now(caseId)) {
        while (true) { value = CaseClock.now(caseId); delay(1_000) }
    }
    val zone = ZoneId.systemDefault()
    val day = caseDay(now, CaseClock.start(context, caseId), zone)
    val time = Instant.ofEpochMilli(now).atZone(zone)
    val skip = { millis: (Long) -> Long ->
        if (!busy) {
            busy = true
            scope.launch {
                withContext(Dispatchers.IO) { skipTime(context, caseId, millis(CaseClock.now(caseId))) }
                busy = false
            }
        }
    }
    Row(
        Modifier.fillMaxWidth().background(Color(0xFFFFD54F)).statusBarsPadding().padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            if (busy) "D$day …" else "D$day ${time.format(DateTimeFormatter.ofPattern("HH:mm"))}",
            color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.widthIn(min = 82.dp),
        )
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            SkipButton(stringResource(R.string.debug_next)) { skip { now -> (nextEvent(ChatModule.of(context, caseId).listChats(), now) ?: now) - now } }
            SkipButton("+10m") { skip { 10 * MINUTE } }
            SkipButton("+1h") { skip { HOUR } }
            SkipButton("+6h") { skip { 6 * HOUR } }
            // The next 08:00, i.e. the next morning of the case.
            SkipButton("08:00 →") {
                skip { now ->
                    val t = Instant.ofEpochMilli(now).atZone(zone)
                    val morning = t.withHour(8).withMinute(0).withSecond(0).withNano(0).let { if (it.isAfter(t)) it else it.plusDays(1) }
                    morning.toInstant().toEpochMilli() - now
                }
            }
        }
    }
}

@Composable
private fun SkipButton(label: String, onClick: () -> Unit) {
    Text(
        label,
        Modifier.background(Color.Black.copy(alpha = 0.12f), RoundedCornerShape(8.dp)).clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
    )
}

/** The next moment the story has something to do: a line arriving or choices expiring. Null if it's waiting for the player. */
internal fun nextEvent(chats: List<Chat>, now: Long): Long? = chats.flatMap { c ->
    c.messages.map { it.deliverAt } + listOfNotNull(c.choicesExpireAt.takeIf { c.choices.isNotEmpty() && it > 0 })
}.filter { it > now }.minOrNull()

/**
 * Moves debug case [caseId] [millis] ahead. The clock stops at every story event on the way, so each timed line
 * arrives and each choice expires in order, exactly as if the time had really passed.
 */
fun skipTime(context: Context, caseId: String, millis: Long) {
    val chat = ChatModule.of(context, caseId)
    val target = CaseClock.now(caseId) + millis
    // ponytail: capped steps, in case a story loops on itself; a whole week is a few hundred events.
    for (step in 0 until 2_000) {
        chat.advanceAll()
        val now = CaseClock.now(caseId)
        val next = nextEvent(chat.listChats(), now)?.takeIf { it <= target } ?: break
        CaseClock.skip(context, caseId, next - now)
    }
    CaseClock.skip(context, caseId, target - CaseClock.now(caseId))
    chat.advanceAll()
}
