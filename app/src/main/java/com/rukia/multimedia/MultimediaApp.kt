package com.rukia.multimedia

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
import com.rukia.phone.CaseFolders
import com.rukia.phone.SystemBars
import com.rukia.phone.rememberCaseImage

val MultimediaOrange = Color(0xFFF57C00)

/** Folder inside the case's content that this app shows. Every image file in it appears in the gallery. */
private const val FOLDER = "multimedia"
private val IMAGE = Regex(""".*\.(jpe?g|png|webp|gif|bmp)""", RegexOption.IGNORE_CASE)

/** Multimedia app: a gallery of the photos in assets/cases/<caseId>/multimedia/. Tap one to see it full screen. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaApp(caseId: String) {
    val assets = LocalContext.current.assets
    val photos = remember(caseId) {
        assets.list("${CaseFolders.content(caseId)}/$FOLDER").orEmpty().filter { IMAGE.matches(it) }.sorted()
    }
    var open by rememberSaveable { mutableStateOf<String?>(null) }

    open?.let { name ->
        BackHandler { open = null }
        SystemBars(lightBottomIcons = true)
        Box(Modifier.fillMaxSize().background(Color.Black).clickable { open = null }, contentAlignment = Alignment.Center) {
            rememberCaseImage("$FOLDER/$name", maxSize = 2048)?.let {
                Image(it, name, Modifier.fillMaxSize().safeDrawingPadding(), contentScale = ContentScale.Fit)
            }
        }
        return
    }

    SystemBars(lightBottomIcons = false)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Multimedia", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MultimediaOrange, titleContentColor = Color.White),
            )
        },
    ) { padding ->
        if (photos.isEmpty()) {
            Box(Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) { Text("No photos", color = Color.Gray) }
        } else {
            LazyVerticalGrid(GridCells.Fixed(3), Modifier.padding(padding), contentPadding = PaddingValues(2.dp)) {
                items(photos) { name ->
                    Box(Modifier.padding(2.dp).aspectRatio(1f).background(Color.LightGray).clickable { open = name }) {
                        rememberCaseImage("$FOLDER/$name")?.let { Image(it, name, Modifier.fillMaxSize(), contentScale = ContentScale.Crop) }
                    }
                }
            }
        }
    }
}
