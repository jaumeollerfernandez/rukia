package com.rukia.multimedia.infrastructure.ui

import androidx.compose.ui.res.stringResource
import com.rukia.R
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rukia.multimedia.domain.model.Photo
import com.rukia.multimedia.domain.port.Gallery
import com.rukia.multimedia.domain.port.StoryFlags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.rukia.multimedia.infrastructure.MultimediaModule
import com.rukia.phone.infrastructure.ui.MugshotPicture
import com.rukia.phone.infrastructure.LocalDarkMode
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.phone.infrastructure.media.rememberCaseImage

val MultimediaOrange = Color(0xFFF57C00)

/**
 * Multimedia app: a gallery of the photos in assets/cases/<caseId>/multimedia/, then the police photos ([mugshots])
 * of the records the player read. Tap one to see it full screen; the story learns it through [flags].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaApp(caseId: String, flags: StoryFlags = StoryFlags {}, mugshots: Gallery = Gallery { emptyList() }) {
    val context = LocalContext.current
    val module = remember(caseId) { MultimediaModule(context.applicationContext, caseId, mugshots, flags) }
    val photos = remember(module) { module.listPhotos() }
    var open by rememberSaveable { mutableStateOf<String?>(null) }

    val start = photos.indexOfFirst { it.path == open }
    if (start >= 0) {
        // Swipe left or right for the next photo; each one shown counts as seen.
        val pager = rememberPagerState(initialPage = start) { photos.size }
        val photo = photos[pager.currentPage]
        LaunchedEffect(photo.path) { open = photo.path; withContext(Dispatchers.IO) { module.viewPhoto(photo) } }
        BackHandler { open = null }
        SystemBars(lightBottomIcons = true)
        HorizontalPager(pager, Modifier.fillMaxSize().background(Color.Black), key = { photos[it].path }) { page ->
            val shown = photos[page]
            Box(Modifier.fillMaxSize().clickable { open = null }, contentAlignment = Alignment.Center) {
                if (shown.plate != null) PhotoTile(shown, Modifier.safeDrawingPadding().fillMaxWidth().aspectRatio(0.8f))
                else rememberCaseImage(shown.path, maxSize = 2048)?.let {
                    Image(it, shown.path, Modifier.fillMaxSize().safeDrawingPadding().pinchZoom(), contentScale = ContentScale.Fit)
                }
            }
        }
        return
    }

    val dark = LocalDarkMode.current
    SystemBars(lightBottomIcons = dark)
    MaterialTheme(colorScheme = if (dark) darkColorScheme() else lightColorScheme()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_multimedia), fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MultimediaOrange, titleContentColor = Color.White),
                )
            },
        ) { padding ->
            if (photos.isEmpty()) {
                Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) { Text(stringResource(R.string.no_photos), color = Color.Gray) }
            } else {
                LazyVerticalGrid(GridCells.Fixed(3), Modifier.padding(padding), contentPadding = PaddingValues(2.dp)) {
                    items(photos) { photo ->
                        Box(Modifier.padding(2.dp).aspectRatio(1f).background(if (dark) Color.DarkGray else Color.LightGray).clickable { open = photo.path }) {
                            PhotoTile(photo, Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}

/**
 * Pinch to zoom (1x-5x) and drag to pan while zoomed. Gestures are only consumed with two fingers or when already
 * zoomed, so the pager still swipes at 1x. Lets go back to 1x when the pinch shrinks it below.
 */
private fun Modifier.pinchZoom(): Modifier = composed {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    pointerInput(Unit) {
        awaitEachGesture {
            awaitFirstDown(requireUnconsumed = false)
            do {
                val event = awaitPointerEvent()
                val multi = event.changes.size > 1
                if (multi || scale > 1f) {
                    val zoom = event.calculateZoom()
                    scale = (scale * zoom).coerceIn(1f, 5f)
                    offset = if (scale == 1f) Offset.Zero else {
                        val maxX = size.width * (scale - 1) / 2
                        val maxY = size.height * (scale - 1) / 2
                        val o = offset + event.calculatePan()
                        Offset(o.x.coerceIn(-maxX, maxX), o.y.coerceIn(-maxY, maxY))
                    }
                    event.changes.forEach { it.consume() }
                }
            } while (event.changes.any { it.pressed })
        }
    }.graphicsLayer { scaleX = scale; scaleY = scale; translationX = offset.x; translationY = offset.y }
}

/** A photo filling [modifier]: a police photo drawn with its plate until its picture exists, any other one as it is. */
@Composable
private fun PhotoTile(photo: Photo, modifier: Modifier) {
    val plate = photo.plate
    if (plate != null) MugshotPicture(plate, photo.profile, photo.path, stringResource(if (photo.profile) R.string.mugshot_profile else R.string.mugshot_front), modifier)
    else rememberCaseImage(photo.path)?.let { Image(it, photo.path, modifier, contentScale = ContentScale.Crop) }
}
