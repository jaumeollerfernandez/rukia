package com.rukia.gonpi

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.phone.CaseClock
import com.rukia.phone.CaseFolders
import com.rukia.phone.SystemBars
import com.rukia.phone.rememberCaseImage
import com.rukia.phone.caseTime
import com.rukia.phone.rememberMediaImage
import java.io.File
import java.time.ZoneId

val GonpiPink = Color(0xFFE1306C)
private val Subtle = Color.White.copy(alpha = 0.6f)

/** Gonpi, the phone's Instagram: a feed of the posts in assets/cases/<caseId>/gonpi/gonpi.json, and each account's profile. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GonpiApp(caseId: String) {
    SystemBars(lightBottomIcons = true)
    val context = LocalContext.current
    val assets = context.assets
    // A case without the file just has an empty Gonpi; a broken file fails loudly (and in GonpiTest).
    // Only what's been published by now in the case's week shows; reopening the app picks up newer posts.
    val gonpi = remember(caseId) {
        val all = runCatching { assets.open("${CaseFolders.content(caseId)}/${Gonpi.PATH}") }.getOrNull()
            ?.use { Gonpi.parse(it.bufferedReader().readText()) } ?: Gonpi()
        val start = CaseClock.start(context, caseId)
        all.publishedBy(System.currentTimeMillis()) { caseTime(it, start, ZoneId.systemDefault()) }
    }
    var profileId by rememberSaveable { mutableStateOf<String?>(null) }
    var postIndex by rememberSaveable { mutableStateOf<Int?>(null) }
    BackHandler(enabled = profileId != null || postIndex != null) { if (postIndex != null) postIndex = null else profileId = null }
    val openProfile = { id: String -> profileId = id; postIndex = null }
    // The player's likes, one post key per line, kept with the case's saves.
    val likesFile = remember(caseId) { File(CaseFolders.saves(context, caseId, "gonpi"), "likes.txt") }
    val liked = remember(caseId) { mutableStateListOf<String>().apply { if (likesFile.exists()) addAll(likesFile.readLines()) } }
    val toggleLike = { post: Post ->
        if (!liked.remove(post.key)) liked += post.key
        likesFile.writeText(liked.joinToString("\n"))
    }
    val card = @Composable { post: Post -> PostCard(gonpi, post, post.key in liked, { toggleLike(post) }, openProfile) }

    MaterialTheme(colorScheme = darkColorScheme(primary = GonpiPink, background = Color.Black, surface = Color.Black)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        val profile = profileId?.let(gonpi::account)
                        if (postIndex != null) Text("Post", fontWeight = FontWeight.Bold)
                        else if (profile != null) Text(profile.username, fontWeight = FontWeight.Bold)
                        else Text("Gonpi", fontFamily = FontFamily.Cursive, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                    },
                    navigationIcon = {
                        if (profileId != null || postIndex != null) {
                            IconButton({ if (postIndex != null) postIndex = null else profileId = null }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black),
                )
            },
            containerColor = Color.Black,
        ) { padding ->
            val post = postIndex?.let(gonpi.posts::get)
            val profile = profileId?.let(gonpi::account)
            when {
                post != null -> LazyColumn(Modifier.padding(padding)) { item { card(post) } }
                profile != null -> ProfileScreen(gonpi, profile, Modifier.padding(padding)) { postIndex = it }
                gonpi.posts.isEmpty() -> Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No posts yet", color = Subtle)
                }
                else -> LazyColumn(Modifier.padding(padding)) {
                    items(gonpi.posts) { card(it); Spacer(Modifier.height(12.dp)) }
                }
            }
        }
    }
}

@Composable
private fun PostCard(gonpi: Gonpi, post: Post, liked: Boolean, onLike: () -> Unit, onOpenProfile: (String) -> Unit) {
    val author = gonpi.account(post.author)
    var showComments by remember(post) { mutableStateOf(false) }
    Column {
        Row(
            Modifier.fillMaxWidth().clickable { onOpenProfile(author.id) }.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AccountAvatar(author, 34)
            Text(author.username, Modifier.padding(start = 10.dp), fontWeight = FontWeight.SemiBold)
        }
        // Double-tap the photo to like it, like Instagram (it only likes, never unlikes).
        PostImage(post.image, Modifier.fillMaxWidth().aspectRatio(1f).pointerInput(liked) { detectTapGestures(onDoubleTap = { if (!liked) onLike() }) })
        Column(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder, if (liked) "Unlike" else "Like",
                Modifier.size(26.dp).clickable(onClick = onLike), tint = if (liked) GonpiPink else LocalContentColor.current,
            )
            Text("${post.likes + if (liked) 1 else 0} likes", fontWeight = FontWeight.SemiBold)
            if (post.caption.isNotEmpty()) UserText(author.username, post.caption)
            if (post.comments.isNotEmpty() && !showComments) {
                Text("View all ${post.comments.size} comments", Modifier.clickable { showComments = true }, color = Subtle)
            }
            if (showComments) post.comments.forEach { c ->
                UserText(gonpi.account(c.author).username, c.text, Modifier.clickable { onOpenProfile(c.author) })
            }
            if (post.time.isNotEmpty()) Text(post.time, color = Subtle, fontSize = 12.sp)
        }
    }
}

@Composable
private fun ProfileScreen(gonpi: Gonpi, account: Account, modifier: Modifier, onOpenPost: (index: Int) -> Unit) {
    val posts = gonpi.posts.withIndex().filter { it.value.author == account.id }
    LazyVerticalGrid(GridCells.Fixed(3), modifier) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AccountAvatar(account, 86)
                    Stat(posts.size, "posts", Modifier.weight(1f))
                    Stat(account.followers, "followers", Modifier.weight(1f))
                    Stat(account.following, "following", Modifier.weight(1f))
                }
                if (account.name.isNotEmpty()) Text(account.name, Modifier.padding(top = 10.dp), fontWeight = FontWeight.SemiBold)
                if (account.bio.isNotEmpty()) Text(account.bio)
                if (posts.isEmpty()) Text("No posts yet", Modifier.padding(top = 32.dp).fillMaxWidth(), color = Subtle)
            }
        }
        items(posts) { (i, post) -> PostImage(post.image, Modifier.padding(1.dp).aspectRatio(1f).clickable { onOpenPost(i) }) }
    }
}

@Composable
private fun Stat(value: Int, label: String, modifier: Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("$value", fontWeight = FontWeight.Bold, fontSize = 17.sp)
        Text(label, fontSize = 13.sp)
    }
}

/** "username text", with the username in bold, like a caption or comment. */
@Composable
private fun UserText(username: String, text: String, modifier: Modifier = Modifier) {
    Text(buildAnnotatedString { withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append(username) }; append(" $text") }, modifier)
}

@Composable
private fun PostImage(path: String, modifier: Modifier) {
    Box(modifier.background(Color(0xFF262626))) {
        rememberCaseImage("media/$path", maxSize = 1080)?.let { Image(it, null, Modifier.fillMaxSize(), contentScale = ContentScale.Crop) }
    }
}

@Composable
private fun AccountAvatar(account: Account, size: Int) {
    val photo = rememberMediaImage(account.photo)
    val modifier = Modifier.size(size.dp).clip(CircleShape)
    if (photo != null) return Image(photo, account.username, modifier, contentScale = ContentScale.Crop)
    Box(modifier.background(Color(android.graphics.Color.parseColor(account.color))), contentAlignment = Alignment.Center) {
        Text(account.username.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = (size / 2.4).sp)
    }
}
