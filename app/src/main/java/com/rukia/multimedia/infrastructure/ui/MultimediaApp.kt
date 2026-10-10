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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rukia.multimedia.domain.model.Photo
import com.rukia.multimedia.domain.port.Gallery
import com.rukia.multimedia.infrastructure.MultimediaModule
import com.rukia.phone.infrastructure.ui.MugshotPicture
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.phone.infrastructure.media.rememberCaseImage

val MultimediaOrange = Color(0xFFF57C00)

/**
 * Multimedia app: a gallery of the photos in assets/cases/<caseId>/multimedia/, then the police photos ([mugshots])
 * of the records the player read. Tap one to see it full screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaApp(caseId: String, mugshots: Gallery = Gallery { emptyList() }) {
    val context = LocalContext.current
    val photos = remember(caseId) { MultimediaModule(context.applicationContext, caseId, mugshots).listPhotos() }
    var open by rememberSaveable { mutableStateOf<String?>(null) }

    photos.find { it.path == open }?.let { photo ->
        BackHandler { open = null }
        SystemBars(lightBottomIcons = true)
        Box(Modifier.fillMaxSize().background(Color.Black).clickable { open = null }, contentAlignment = Alignment.Center) {
            if (photo.plate != null) PhotoTile(photo, Modifier.safeDrawingPadding().fillMaxWidth().aspectRatio(0.8f))
            else rememberCaseImage(photo.path, maxSize = 2048)?.let {
                Image(it, photo.path, Modifier.fillMaxSize().safeDrawingPadding(), contentScale = ContentScale.Fit)
            }
        }
        return
    }

    SystemBars(lightBottomIcons = false)
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
                    Box(Modifier.padding(2.dp).aspectRatio(1f).background(Color.LightGray).clickable { open = photo.path }) {
                        PhotoTile(photo, Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}

/** A photo filling [modifier]: a police photo drawn with its plate until its picture exists, any other one as it is. */
@Composable
private fun PhotoTile(photo: Photo, modifier: Modifier) {
    val plate = photo.plate
    if (plate != null) MugshotPicture(plate, photo.profile, photo.path, stringResource(if (photo.profile) R.string.mugshot_profile else R.string.mugshot_front), modifier)
    else rememberCaseImage(photo.path)?.let { Image(it, photo.path, modifier, contentScale = ContentScale.Crop) }
}
