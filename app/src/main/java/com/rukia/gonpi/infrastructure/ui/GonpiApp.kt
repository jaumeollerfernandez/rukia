package com.rukia.gonpi.infrastructure.ui

import com.rukia.gonpi.domain.port.ContactDirectory
import com.rukia.gonpi.infrastructure.GonpiModule
import com.rukia.gonpi.domain.model.Account
import com.rukia.gonpi.domain.model.Gonpi
import com.rukia.gonpi.domain.model.Post
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.infrastructure.ui.PhotoPopup
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.phone.infrastructure.media.rememberCaseImage
import com.rukia.phone.infrastructure.media.rememberMediaImage
import kotlinx.coroutines.delay

val GonpiPink = Color(0xFFE1306C)
private val Ink = Color(0xFFF5F5F5)
private val Subtle = Color(0xFFA8A8A8)
private val Line = Color(0xFF262626)
private val LikeRed = Color(0xFFFF3040)
private val GrandHotel = FontFamily(Font(R.font.grand_hotel))

/** Gonpi's own account for the player: the phone is Alicia's. */
private const val ME = "alicia"

private enum class Tab { Feed, Search, Me }

/**
 * Gonpi, the phone's Instagram: the feed of assets/cases/<caseId>/gonpi/gonpi.json, a search that finds the phone's
 * contacts (only them: strangers are reached through comments and likes), each account's profile, and Alicia's own.
 */
@Composable
fun GonpiApp(caseId: String, contacts: ContactDirectory) {
    SystemBars(lightBottomIcons = true)
    val context = LocalContext.current
    val module = remember(caseId) { GonpiModule(context.applicationContext, caseId, contacts) }
    // Only what's been published by now in the case's week shows; reopening the app picks up newer posts.
    val gonpi = remember(module) { module.getGonpi() }

    var tab by rememberSaveable { mutableStateOf(Tab.Feed) }
    var query by rememberSaveable { mutableStateOf("") }
    // What's open over the tab, newest last: "profile:<account id>" or "post:<post key>". Back closes the top one.
    val stack = rememberSaveable(saver = listSaver(save = { it.toList() }, restore = { it.toMutableStateList() })) { mutableStateListOf<String>() }
    BackHandler(enabled = stack.isNotEmpty() || tab != Tab.Feed) { if (stack.isNotEmpty()) stack.removeAt(stack.lastIndex) else tab = Tab.Feed }
    val openProfile = { id: String -> if (id == ME && stack.isEmpty()) tab = Tab.Me else stack += "profile:$id" }
    val openPost = { post: Post -> stack += "post:${post.key}" }

    // The player's likes, kept with the case's saves.
    var liked by remember(module) { mutableStateOf(module.getLikes()) }
    val toggleLike = { post: Post -> liked = module.toggleLike(post) }
    val card = @Composable { post: Post, full: Boolean ->
        PostCard(gonpi, post, post.key in liked, full, module.publishedAt(post), module.now(), { toggleLike(post) }, openProfile, { openPost(post) })
    }

    Column(Modifier.fillMaxSize().background(Color.Black).statusBarsPadding()) {
        val top = stack.lastOrNull()
        Box(Modifier.weight(1f)) {
            when {
                top?.startsWith("post:") == true -> {
                    val post = gonpi.posts.find { it.key == top.removePrefix("post:") }
                    Column {
                        BackHeader(stringResource(R.string.gonpi_post)) { stack.removeAt(stack.lastIndex) }
                        if (post != null) LazyColumn { item { card(post, true) } }
                    }
                }
                top?.startsWith("profile:") == true -> {
                    val account = gonpi.accounts.find { it.id == top.removePrefix("profile:") }
                    Column {
                        BackHeader(account?.username.orEmpty()) { stack.removeAt(stack.lastIndex) }
                        if (account != null) ProfileGrid(gonpi, account, openPost)
                    }
                }
                tab == Tab.Search -> SearchScreen(gonpi, { module.searchAccounts(gonpi, it) }, query, { query = it }, openProfile, openPost)
                tab == Tab.Me -> Column {
                    Text(gonpi.accounts.find { it.id == ME }?.username.orEmpty(), Modifier.padding(horizontal = 16.dp, vertical = 12.dp), color = Ink, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    gonpi.accounts.find { it.id == ME }?.let { ProfileGrid(gonpi, it, openPost) }
                }
                else -> Column {
                    Text("Gonpi", Modifier.padding(horizontal = 16.dp, vertical = 8.dp), color = Ink, fontFamily = GrandHotel, fontSize = 34.sp)
                    if (gonpi.posts.isEmpty()) Empty(stringResource(R.string.gonpi_no_posts))
                    else LazyColumn { items(gonpi.posts, key = { it.key }) { card(it, false); Spacer(Modifier.height(12.dp)) } }
                }
            }
        }
        BottomBar(gonpi, tab.takeIf { stack.isEmpty() }) { stack.clear(); tab = it }
    }
}

@Composable
private fun BottomBar(gonpi: Gonpi, current: Tab?, onTab: (Tab) -> Unit) {
    Column(Modifier.fillMaxWidth().background(Color.Black).navigationBarsPadding()) {
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Line))
        Row(Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            IconButton({ onTab(Tab.Feed) }, Modifier.size(width = 64.dp, height = 44.dp)) {
                Icon(if (current == Tab.Feed) HomeFilled else HomeOutline, stringResource(R.string.gonpi_home), Modifier.size(27.dp), tint = Ink)
            }
            IconButton({ onTab(Tab.Search) }, Modifier.size(width = 64.dp, height = 44.dp)) {
                Icon(if (current == Tab.Search) SearchBold else SearchIcon, stringResource(R.string.gonpi_search), Modifier.size(27.dp), tint = Ink)
            }
            val me = gonpi.accounts.find { it.id == ME }
            IconButton({ onTab(Tab.Me) }, Modifier.size(width = 64.dp, height = 44.dp)) {
                // The current tab rings the avatar, as Instagram does.
                val ring = if (current == Tab.Me) Modifier.border(2.dp, Ink, CircleShape).padding(3.dp) else Modifier.padding(2.dp)
                Box(Modifier.size(32.dp).then(ring)) { me?.let { AccountAvatar(it, Modifier.fillMaxSize()) } }
            }
        }
    }
}

@Composable
private fun BackHeader(title: String, onBack: () -> Unit) {
    Row(Modifier.fillMaxWidth().height(48.dp).padding(horizontal = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        IconButton(onBack, Modifier.size(44.dp)) { Icon(BackChevron, stringResource(R.string.back), Modifier.size(26.dp), tint = Ink) }
        Text(title, Modifier.weight(1f).padding(end = 44.dp), color = Ink, fontSize = 17.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun PostCard(
    gonpi: Gonpi, post: Post, liked: Boolean, full: Boolean, publishedAt: Long?, now: Long,
    onLike: () -> Unit, onOpenProfile: (String) -> Unit, onOpenPost: () -> Unit,
) {
    val author = gonpi.account(post.author)
    Column {
        Row(
            Modifier.fillMaxWidth().clickable { onOpenProfile(author.id) }.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AccountAvatar(author, Modifier.size(34.dp))
            Column(Modifier.padding(start = 10.dp)) {
                Text(author.username, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (post.sponsored) Text(stringResource(R.string.gonpi_sponsored), color = Ink, fontSize = 12.sp)
            }
        }
        // Double-tap the photo to like it, with the heart burst; it only likes, never unlikes.
        var burst by remember(post) { mutableStateOf(false) }
        LaunchedEffect(burst) { if (burst) { delay(700); burst = false } }
        val burstScale by animateFloatAsState(if (burst) 1f else 0.4f, label = "burst")
        Box(Modifier.fillMaxWidth().aspectRatio(1f).pointerInput(liked) { detectTapGestures(onDoubleTap = { if (!liked) onLike(); burst = true }) }, contentAlignment = Alignment.Center) {
            PostImage(post.image, Modifier.fillMaxSize())
            Icon(HeartFilled, null, Modifier.size(96.dp).scale(burstScale).alpha(if (burst) 1f else 0f), tint = Color.White)
        }
        Column(Modifier.padding(start = 14.dp, end = 14.dp, top = 4.dp, bottom = 14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onLike, Modifier.size(40.dp).offset(x = (-8).dp)) {
                    Icon(if (liked) HeartFilled else HeartOutline, stringResource(if (liked) R.string.unlike else R.string.like), Modifier.size(26.dp), tint = if (liked) LikeRed else Ink)
                }
                if (full) Icon(CommentIcon, null, Modifier.size(25.dp).offset(x = (-8).dp), tint = Ink)
            }
            Text(stringResource(R.string.likes, post.likes + if (liked) 1 else 0), color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            if (post.caption.isNotEmpty()) {
                UserText(author.username, post.caption, if (full) Modifier else Modifier.clickable(onClick = onOpenPost), maxLines = if (full) Int.MAX_VALUE else 3)
            }
            if (post.comments.isNotEmpty()) {
                if (full) {
                    Text(pluralStringResource(R.plurals.gonpi_comments, post.comments.size, post.comments.size), color = Subtle, fontSize = 13.sp)
                    post.comments.forEach { c -> UserText(gonpi.account(c.author).username, c.text, Modifier.clickable { onOpenProfile(c.author) }) }
                } else {
                    Text(stringResource(R.string.view_comments, post.comments.size), Modifier.clickable(onClick = onOpenPost), color = Subtle, fontSize = 14.sp)
                }
            }
            PostTime(post, publishedAt, now)
        }
    }
}

/** "2 hours ago": the post's own [Post.time], or how long ago it went up if it has a case time. */
@Composable
private fun PostTime(post: Post, at: Long?, now: Long) {
    val text = when {
        post.time.isNotEmpty() -> post.time
        at == null -> return
        else -> {
            val minutes = ((now - at) / 60_000).coerceAtLeast(1).toInt()
            val days = minutes / (24 * 60)
            when {
                minutes < 60 -> pluralStringResource(R.plurals.gonpi_minutes_ago, minutes, minutes)
                days < 1 -> pluralStringResource(R.plurals.gonpi_hours_ago, minutes / 60, minutes / 60)
                days < 7 -> pluralStringResource(R.plurals.gonpi_days_ago, days, days)
                days < 60 -> pluralStringResource(R.plurals.gonpi_weeks_ago, days / 7, days / 7)
                else -> pluralStringResource(R.plurals.gonpi_months_ago, days / 30, days / 30)
            }
        }
    }
    Text(text, color = Subtle, fontSize = 12.sp)
}

@Composable
private fun SearchScreen(
    gonpi: Gonpi, search: (String) -> List<Account>, query: String, onQuery: (String) -> Unit,
    onOpenProfile: (String) -> Unit, onOpenPost: (Post) -> Unit,
) {
    Column {
        BasicTextField(
            query, onQuery,
            Modifier.fillMaxWidth().padding(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 10.dp).height(38.dp)
                .clip(RoundedCornerShape(10.dp)).background(Line),
            singleLine = true,
            textStyle = TextStyle(color = Ink, fontSize = 16.sp),
            cursorBrush = SolidColor(Ink),
            decorationBox = { field ->
                Row(Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    Icon(SearchBold, null, Modifier.size(17.dp), tint = Subtle)
                    Box(Modifier.weight(1f)) {
                        if (query.isEmpty()) Text(stringResource(R.string.gonpi_search), color = Subtle, fontSize = 16.sp)
                        field()
                    }
                }
            },
        )
        if (query.isBlank()) {
            // Explore: every post, as a grid.
            PostGrid(gonpi.posts, onOpenPost)
        } else {
            val results = search(query)
            if (results.isEmpty()) Text(
                stringResource(R.string.gonpi_no_accounts, query.trim()), Modifier.fillMaxWidth().padding(24.dp),
                color = Subtle, fontSize = 14.sp, textAlign = TextAlign.Center,
            )
            LazyColumn {
                items(results, key = { it.id }) { account ->
                    Row(
                        Modifier.fillMaxWidth().clickable { onOpenProfile(account.id) }.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        AccountAvatar(account, Modifier.size(44.dp))
                        Column(Modifier.weight(1f)) {
                            Text(account.username, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(listOf(account.name, account.bio).filter { it.isNotEmpty() }.joinToString(" · "), color = Subtle, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileGrid(gonpi: Gonpi, account: Account, onOpenPost: (Post) -> Unit) {
    val posts = gonpi.postsBy(account.id)
    PostGrid(posts, onOpenPost) {
        Column(Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                AccountAvatar(account, Modifier.size(86.dp), zoomable = true)
                Row(Modifier.weight(1f)) {
                    Stat(posts.size, stringResource(R.string.posts), Modifier.weight(1f))
                    Stat(account.followers, stringResource(R.string.followers), Modifier.weight(1f))
                    Stat(account.following, stringResource(R.string.following), Modifier.weight(1f))
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                if (account.name.isNotEmpty()) Text(account.name, color = Ink, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                if (account.bio.isNotEmpty()) Text(account.bio, color = Ink, fontSize = 14.sp, lineHeight = 19.sp)
            }
        }
        Box(Modifier.fillMaxWidth().height(0.5.dp).background(Line))
        Box(Modifier.fillMaxWidth().height(44.dp), contentAlignment = Alignment.Center) {
            Icon(GridIcon, stringResource(R.string.posts), Modifier.size(22.dp), tint = Ink)
            Box(Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(1.5.dp).background(Ink))
        }
        if (posts.isEmpty()) Empty(stringResource(R.string.gonpi_no_posts))
    }
}

/** Posts as a three-column grid of square thumbnails, under an optional [header]. */
@Composable
private fun PostGrid(posts: List<Post>, onOpenPost: (Post) -> Unit, header: (@Composable () -> Unit)? = null) {
    LazyVerticalGrid(GridCells.Fixed(3), horizontalArrangement = Arrangement.spacedBy(2.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        if (header != null) item(span = { GridItemSpan(maxLineSpan) }) { Column { header() } }
        items(posts, key = { it.key }) { post -> PostImage(post.image, Modifier.aspectRatio(1f).clickable { onOpenPost(post) }, maxSize = 360) }
    }
}

@Composable
private fun Stat(value: Int, label: String, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("%,d".format(value), color = Ink, fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Text(label, color = Ink, fontSize = 13.sp)
    }
}

@Composable
private fun Empty(text: String) {
    Text(text, Modifier.fillMaxWidth().padding(top = 32.dp), color = Subtle, textAlign = TextAlign.Center)
}

/** "username text", with the username in bold, like a caption or comment. */
@Composable
private fun UserText(username: String, text: String, modifier: Modifier = Modifier, maxLines: Int = Int.MAX_VALUE) {
    Text(
        buildAnnotatedString { withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(username) }; append(" $text") },
        modifier, color = Ink, fontSize = 14.sp, lineHeight = 19.sp, maxLines = maxLines, overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun PostImage(path: String, modifier: Modifier, maxSize: Int = 1080) {
    Box(modifier.background(Line)) {
        rememberCaseImage("media/$path", maxSize)?.let { Image(it, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop) }
    }
}

/** The account's round photo, or its initial on its color. [zoomable]: tapping it shows the photo up close, as Instagram does. */
@Composable
private fun AccountAvatar(account: Account, modifier: Modifier, zoomable: Boolean = false) {
    val photo = rememberMediaImage(account.photo)
    var zoomed by remember { mutableStateOf(false) }
    if (zoomed) PhotoPopup(account.photo, account.username, Color(android.graphics.Color.parseColor(account.color))) { zoomed = false }
    val round = modifier.clip(CircleShape).then(if (zoomable) Modifier.clickable(onClickLabel = account.username) { zoomed = true } else Modifier)
    if (photo != null) return Image(photo, account.username, round, contentScale = ContentScale.Crop)
    BoxWithConstraints(round.background(Color(android.graphics.Color.parseColor(account.color))), contentAlignment = Alignment.Center) {
        Text(account.username.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = (maxWidth.value / 2.4f).sp)
    }
}

// The design's icons, from its SVG paths (24×24).
private fun icon(d: String, filled: Boolean, stroke: Float = 1.9f) = ImageVector.Builder(defaultWidth = 24.dp, defaultHeight = 24.dp, viewportWidth = 24f, viewportHeight = 24f)
    .addPath(
        PathParser().parsePathString(d).toNodes(),
        fill = if (filled) SolidColor(Color.White) else null,
        stroke = if (filled) null else SolidColor(Color.White),
        strokeLineWidth = stroke, strokeLineCap = StrokeCap.Round, strokeLineJoin = StrokeJoin.Round,
    ).build()

private const val HEART = "M12 21s-7.5-4.6-9.6-9.2C.9 8.4 3 4.5 6.7 4.5c2.2 0 3.6 1.2 4.3 2.4.7-1.2 2.1-2.4 4.3-2.4 3.7 0 5.8 3.9 4.3 7.3C19.5 16.4 12 21 12 21z"
private const val HOME = "M3 10.2L12 3l9 7.2V21h-6.5v-6.5h-5V21H3z"
private const val SEARCH = "M10.5 3.5a7 7 0 1 0 0 14a7 7 0 1 0 0-14zM16 16l5 5"
private val HeartFilled = icon(HEART, filled = true)
private val HeartOutline = icon(HEART, filled = false)
private val HomeFilled = icon(HOME, filled = true)
private val HomeOutline = icon(HOME, filled = false)
private val SearchIcon = icon(SEARCH, filled = false, stroke = 2f)
private val SearchBold = icon(SEARCH, filled = false, stroke = 3f)
private val BackChevron = icon("M15 5l-7 7 7 7", filled = false, stroke = 2.2f)
private val CommentIcon = icon("M20.5 11.5a8.5 8.5 0 0 1-12.6 7.4L3.5 20.5l1.6-4.3A8.5 8.5 0 1 1 20.5 11.5z", filled = false)
private val GridIcon = icon("M4.5 3h15A1.5 1.5 0 0 1 21 4.5v15a1.5 1.5 0 0 1-1.5 1.5h-15A1.5 1.5 0 0 1 3 19.5v-15A1.5 1.5 0 0 1 4.5 3zM9 3v18M15 3v18M3 9h18M3 15h18", filled = false, stroke = 1.8f)
