package com.rukia.chat.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.chat.domain.model.CallStatus
import com.rukia.chat.domain.model.Character
import com.rukia.chat.domain.model.Chat
import com.rukia.chat.domain.model.Message
import com.rukia.chat.infrastructure.ChatModule
import com.rukia.chat.infrastructure.notifications.VisibleChat
import com.rukia.game.infrastructure.ui.Barlow
import com.rukia.game.infrastructure.ui.CutCorners
import com.rukia.game.infrastructure.ui.Rajdhani
import com.rukia.game.infrastructure.ui.Term
import com.rukia.game.infrastructure.ui.mono
import com.rukia.phone.domain.model.caseTimeLabel
import com.rukia.phone.infrastructure.CaseClock
import com.rukia.phone.infrastructure.LocalCaseId
import com.rukia.phone.infrastructure.media.rememberMediaImage
import com.rukia.phone.infrastructure.ui.ImageViewer
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.ZoneId

// The secure channel's look, from the game's terminal (design: PoliceChat).
private val Bar = Color(0xFF0B121B)
private val TheirBubble = Color(0xFF101B27)
private val AvatarGround = Color(0xFF1C2A3A)

/** "LP" for "Sgt. Laia Puig": the initials of the name's words, ranks ("Sgt.") left out. */
fun initials(name: String) = name.split(' ').filter { it.firstOrNull()?.isUpperCase() == true && !it.endsWith('.') }
    .take(2).joinToString("") { it.take(1) }.ifEmpty { name.take(1) }

/** "SGT. PUIG" for "Sgt. Laia Puig": rank and surname, as the channel signs her lines. */
private fun signature(name: String) = name.split(' ').let { if (it.size > 2) "${it.first()} ${it.last()}" else name }.uppercase()

/** Square avatar with the initials and a green dot: the contact on the other end of a secure channel. */
@Composable
fun ChannelAvatar(name: String, size: Int, ring: Color = Bar) {
    Box(Modifier.size(size.dp).background(AvatarGround).border(1.dp, Term.Cyan), contentAlignment = Alignment.Center) {
        Text(initials(name), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = (size * 0.4f).sp, color = Term.Cyan)
        Box(Modifier.align(Alignment.BottomEnd).offset(4.dp, 4.dp).size(12.dp).background(ring, CircleShape).padding(2.dp).background(Term.Green, CircleShape))
    }
}

/**
 * The chat with [contactId] as a secure channel inside another app (the sergeant's, in Police): the same story and
 * choices as in Chats, with the terminal's look. Choosing a reply is two steps, pick then send, as in the design.
 */
@Composable
fun SecureChannel(caseId: String, contactId: String, onBack: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    BackHandler(onBack = onBack)
    val context = LocalContext.current.applicationContext
    val m = remember(caseId) { ChatModule.of(context, caseId) }
    DisposableEffect(caseId) {
        VisibleChat.caseId = caseId
        onDispose { if (VisibleChat.caseId == caseId) VisibleChat.caseId = null }
    }
    val find = { m.listChats().find { it.participants == listOf(contactId) } }
    // What already arrived shows at once; anything newer is animated in.
    var revealFrom by rememberSaveable { mutableIntStateOf(-1) }
    var chat by remember {
        mutableStateOf(find()?.let { m.advanceChat(it.id); find() })
    }
    if (revealFrom < 0) revealFrom = chat?.arrivedCount(CaseClock.now(caseId)) ?: 0
    LaunchedEffect(m) {
        while (true) {
            delay(2_000)
            chat = withContext(Dispatchers.IO) { find()?.also { m.advanceChat(it.id) }?.let { find() } }
        }
    }
    val contact = m.characters[contactId]
    val name = contact?.name ?: contactId
    Column(Modifier.fillMaxSize().drawBehind {
        drawRect(Term.Ground)
        val step = 32.dp.toPx()
        val grid = Term.Cyan.copy(alpha = 0.04f)
        var x = 0f
        while (x < size.width) { drawLine(grid, Offset(x, 0f), Offset(x, size.height), 1f); x += step }
        var y = 0f
        while (y < size.height) { drawLine(grid, Offset(0f, y), Offset(size.width, y), 1f); y += step }
    }) {
        ChannelHeader(name, contact?.status.orEmpty(), onBack)
        val current = chat
        if (current == null) Spacer(Modifier.weight(1f))
        else ChannelLog(current, m.characters, revealFrom, Modifier.weight(1f),
            onRead = { m.markChatRead(current.id, it) },
            onChoose = { m.chooseReply(current.id, it); chat = find() },
        )
    }
}

@Composable
private fun ChannelHeader(name: String, status: String, onBack: () -> Unit) {
    val back = stringResource(R.string.back)
    Row(
        Modifier.fillMaxWidth().background(Bar).drawBehind {
            drawLine(Term.Cyan.copy(alpha = 0.25f), Offset(0f, size.height), Offset(size.width, size.height), 1.dp.toPx())
        }.statusBarsPadding().padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Box(
            Modifier.size(40.dp).border(1.dp, Term.Cyan.copy(alpha = 0.35f)).clickable(role = Role.Button, onClick = onBack)
                .semantics { contentDescription = back },
            contentAlignment = Alignment.Center,
        ) { Icon(RukiaIcons.ArrowLeft, null, Modifier.size(20.dp), tint = Term.Text) }
        ChannelAvatar(name, 42)
        Column(Modifier.weight(1f)) {
            Text(name, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 20.sp, color = Term.Text)
            if (status.isNotEmpty()) Text(status, fontFamily = Barlow, fontSize = 12.sp, color = Term.Muted)
        }
        Row(
            Modifier.border(1.dp, Term.Green.copy(alpha = 0.5f)).padding(horizontal = 7.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(RukiaIcons.Lock, null, Modifier.size(10.dp), tint = Term.Green)
            Text(stringResource(R.string.channel_encrypted), style = mono(9, Term.Green, spacing = 0f))
        }
    }
}

@Composable
private fun ChannelLog(
    chat: Chat, characters: Map<String, Character>, revealFrom: Int, modifier: Modifier,
    onRead: (Int) -> Unit, onChoose: (Int) -> Unit,
) {
    val caseId = LocalCaseId.current
    val context = LocalContext.current.applicationContext
    val reveal = rememberReveal(chat, revealFrom, onRead)
    val visible = chat.messages.take(reveal.shown)
    val listState = rememberLazyListState()
    LaunchedEffect(reveal.shown, reveal.typingId) {
        val last = reveal.shown - 1 + if (reveal.typingId != null) 1 else 0
        if (last >= 0) listState.animateScrollToItem(last)
    }
    val start = remember(caseId) { CaseClock.start(context, caseId) }
    val zone = remember { ZoneId.systemDefault() }
    val you = stringResource(R.string.you).uppercase()
    Box(modifier) {
        Column(Modifier.fillMaxSize()) {
            LazyColumn(Modifier.weight(1f), state = listState, contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(visible) { i, msg ->
                    val prev = visible.getOrNull(i - 1)
                    // A pill with the case day and time when the day changes.
                    val label = if (msg.deliverAt > 0) caseTimeLabel(msg.deliverAt, start, zone) else ""
                    if (label.isNotEmpty() && (prev == null || prev.deliverAt <= 0 || caseTimeLabel(prev.deliverAt, start, zone).substringBefore(' ') != label.substringBefore(' '))) {
                        Box(Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = Alignment.Center) {
                            Text(label.replace(" ", " · "), Modifier.border(1.dp, Term.Amber.copy(alpha = 0.5f)).padding(horizontal = 10.dp, vertical = 3.dp), style = mono(10, Term.Amber, spacing = 1f))
                        }
                    }
                    val who = if (msg.fromPlayer) you else signature(characters[msg.from]?.name ?: msg.from)
                    if (msg.call == null) ChannelBubble(msg, listOf(who, msg.time).filter { it.isNotEmpty() }.joinToString(" · "))
                }
                if (reveal.typingId != null) item(key = "typing") { ChannelTyping() }
            }
            val choices = chat.visibleChoices()
            if (reveal.shown == chat.messages.size && reveal.ringing == null && choices.isNotEmpty()) {
                ChannelChoices(choices, chat.choicesExpireAt.takeIf { it > 0 }?.let { caseTimeLabel(it, start, zone).substringAfter(' ') }, onChoose)
            } else Spacer(Modifier.navigationBarsPadding().height(8.dp))
        }
    }
}

/** A line of the channel: theirs on the left with a cyan edge, the player's on the right with an amber one; who and when below. */
@Composable
private fun ChannelBubble(msg: Message, meta: String) {
    val mine = msg.fromPlayer
    val enter = remember { Animatable(0f) }
    LaunchedEffect(Unit) { enter.animateTo(1f, tween(250)) }
    Column(
        Modifier.fillMaxWidth().alpha(enter.value).offset(y = ((1 - enter.value) * 4).dp),
        horizontalAlignment = if (mine) Alignment.End else Alignment.Start, verticalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        var viewing by remember { mutableStateOf(false) }
        rememberMediaImage(msg.image)?.let {
            Image(it, null, Modifier.widthIn(max = 270.dp).clickable(role = Role.Image) { viewing = true }, contentScale = ContentScale.FillWidth)
        }
        if (viewing && msg.image != null) ImageViewer(msg.image) { viewing = false }
        val edge = if (mine) Term.Amber else Term.Cyan
        Text(
            msg.text,
            Modifier.fillMaxWidth(0.86f).wrapContentWidth(if (mine) Alignment.End else Alignment.Start)
                .background(if (mine) Term.Amber.copy(alpha = 0.12f) else TheirBubble)
                .drawBehind {
                    val w = 3.dp.toPx()
                    drawRect(edge, topLeft = Offset(if (mine) size.width - w else 0f, 0f), size = androidx.compose.ui.geometry.Size(w, size.height))
                }
                .padding(horizontal = 12.dp, vertical = 9.dp),
            fontFamily = Barlow, fontSize = 15.sp, lineHeight = 21.sp, color = Term.Text,
        )
        Text(meta, style = mono(9, Term.Dim, spacing = 0f))
    }
}

@Composable
private fun ChannelTyping() {
    val transition = rememberInfiniteTransition(label = "typing")
    Row(
        Modifier.background(TheirBubble).drawBehind { drawRect(Term.Cyan, size = androidx.compose.ui.geometry.Size(3.dp.toPx(), size.height)) }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        repeat(3) { i ->
            val alpha by transition.animateFloat(0.25f, 1f, infiniteRepeatable(tween(600), RepeatMode.Reverse, initialStartOffset = StartOffset(i * 200)), label = "dot$i")
            Box(Modifier.size(7.dp).alpha(alpha).background(Term.Cyan, CircleShape))
        }
    }
}

/** The replies the player can send: pick one, then send it. [until] is when they expire ("08:25"), if they do. */
@Composable
private fun ChannelChoices(choices: List<IndexedValue<String>>, until: String?, onChoose: (Int) -> Unit) {
    var picked by remember(choices) { mutableStateOf<Int?>(null) }
    var sent by remember(choices) { mutableStateOf(false) }
    var folded by rememberSaveable(choices) { mutableStateOf(false) }
    Column(
        Modifier.fillMaxWidth().navigationBarsPadding().padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 12.dp)
            .background(Color(0xF20C1620)).border(1.dp, Term.Amber.copy(alpha = 0.6f)).padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        FoldHeader(folded, choices.size, { folded = !folded }, color = Term.Amber) {
            Row(Modifier.fillMaxWidth().padding(end = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.channel_reply), style = mono(11, Term.Amber, FontWeight.SemiBold, 1.5f))
                until?.let { Text(stringResource(R.string.channel_until, it), style = mono(10, Term.Muted, spacing = 0f)) }
            }
        }
        if (folded) return@Column
        choices.forEach { (i, text) ->
            val on = picked == i
            Text(
                text,
                Modifier.fillMaxWidth().background(if (on) Term.Amber.copy(alpha = 0.12f) else Color(0x99070B12))
                    .border(if (on) 2.dp else 1.dp, if (on) Term.Amber else Term.Cyan.copy(alpha = 0.3f))
                    .clickable(enabled = !sent, role = Role.RadioButton) { picked = i }.padding(horizontal = 12.dp, vertical = 10.dp),
                fontFamily = Barlow, fontSize = 15.sp, lineHeight = 20.sp, color = Term.Text,
            )
        }
        val ready = picked != null && !sent
        Box(
            Modifier.fillMaxWidth().heightIn(min = 50.dp).clip(CutCorners).background(if (ready) Term.Amber else Term.Muted.copy(alpha = 0.15f))
                .clickable(enabled = ready, role = Role.Button) { sent = true; onChoose(picked!!) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                stringResource(if (picked == null) R.string.channel_pick else R.string.channel_send),
                fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 17.sp, letterSpacing = 2.sp,
                color = if (ready) Term.Ink else Term.Muted,
            )
        }
    }
}
