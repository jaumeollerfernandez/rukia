package com.rukia.police.infrastructure.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rukia.R
import com.rukia.phone.infrastructure.media.rememberMediaImage
import com.rukia.phone.infrastructure.ui.Kit
import com.rukia.phone.infrastructure.ui.MugshotPicture
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.police.application.ListMugshots
import com.rukia.police.domain.model.Fingerprints
import com.rukia.police.domain.model.OpenedRecord
import com.rukia.police.domain.model.PoliceRecord
import com.rukia.police.domain.model.RecordRow
import com.rukia.police.domain.model.RecordSection
import com.rukia.police.domain.model.RecordStatus
import com.rukia.police.domain.model.RecordsBoard

// Police records (design: PoliceRecords, RecordAlicia, RecordDani). The records' text is case content; the frame is translated.

private val Mono = FontFamily.Monospace
private fun mono(size: Int, color: Color, weight: FontWeight = FontWeight.Normal) =
    TextStyle(fontFamily = Mono, fontSize = size.sp, color = color, fontWeight = weight, letterSpacing = 0.5.sp)

/** Badge colors by tone: text on a pale background, as in the design. */
private fun tone(name: String): Pair<Color, Color> = when (name) {
    "missing" -> Color(0xFF9F1D16) to Color(0xFFFDE7E5)
    "police" -> Color(0xFF8A4B00) to Color(0xFFFFF0D9)
    "complainant" -> Color(0xFF0F4FC7) to Color(0xFFE5EDFD)
    "mentioned" -> Color(0xFF5B2A9E) to Color(0xFFEFE7FB)
    "witness" -> Color(0xFF1E7A36) to Color(0xFFE3F5E8)
    else -> Color(0xFF45454A) to Color(0xFFECECF0)
}

@Composable
private fun Badge(text: String, toneName: String, size: Int = 10) {
    val (fg, bg) = tone(toneName)
    Text(text, Modifier.background(bg, RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp), style = mono(size, fg, FontWeight.SemiBold))
}

/**
 * Records the player can ask for, people and vehicles, with a search. A record has to be requested first and comes
 * in hours later; one at a time. [onRequest] asks for one, [onOpen] opens one that came in.
 */
@Composable
fun RecordsScreen(caseTitle: String, board: RecordsBoard, caseTimeOf: (Long) -> String, onBack: () -> Unit, onRequest: (String) -> Unit, onOpen: (String) -> Unit) {
    BackHandler(onBack = onBack)
    val c = LocalPolice.current
    var query by rememberSaveable { mutableStateOf("") }
    var vehicles by rememberSaveable { mutableStateOf(false) }
    var asking by remember { mutableStateOf<RecordRow?>(null) }
    val rows = board.rows.filter { it.record.vehicle == vehicles && it.record.matches(query) }
    val pendingName = board.pending?.let { p -> board.rows.find { it.record.id == p.record }?.record?.name }

    Column(Modifier.fillMaxSize().background(c.background)) {
        SubHeader("", onBack)
        Column(Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(stringResource(R.string.records_kicker, caseTitle), style = mono(11, c.subText, FontWeight.SemiBold))
                Text(stringResource(R.string.police_records), fontSize = 32.sp, lineHeight = 38.sp, fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp)
            }
            Row(
                Modifier.fillMaxWidth().height(38.dp).background(c.separator, RoundedCornerShape(10.dp)).padding(horizontal = 11.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(RukiaIcons.Search, null, Modifier.size(17.dp), tint = c.subText)
                val hint = stringResource(R.string.records_search)
                Box(Modifier.weight(1f)) {
                    if (query.isEmpty()) Text(hint, color = c.subText, fontSize = 16.sp)
                    BasicTextField(
                        query, { query = it }, Modifier.fillMaxWidth().semantics { contentDescription = hint }, singleLine = true,
                        textStyle = TextStyle(color = c.text, fontSize = 16.sp), cursorBrush = SolidColor(c.tint),
                    )
                }
            }
            Row(Modifier.fillMaxWidth().background(c.separator, RoundedCornerShape(9.dp)).padding(2.dp).selectableGroup()) {
                listOf(false to R.string.records_people, true to R.string.records_vehicles).forEach { (isVehicles, label) ->
                    val on = vehicles == isVehicles
                    Box(
                        Modifier.weight(1f).height(32.dp).clip(RoundedCornerShape(7.dp)).background(if (on) c.card else Color.Transparent)
                            .selectable(on, role = Role.Tab) { vehicles = isVehicles },
                        contentAlignment = Alignment.Center,
                    ) { Text(stringResource(label), fontSize = 14.sp, fontWeight = FontWeight.SemiBold) }
                }
            }
        }
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(start = 16.dp, end = 16.dp, top = 12.dp)) {
            Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(c.card)) {
                rows.forEachIndexed { i, row ->
                    if (i > 0) HorizontalDivider(thickness = 0.5.dp, color = c.separator)
                    RecordRowView(row, board.hoursNow, caseTimeOf) {
                        when (row.status) {
                            RecordStatus.Ready, RecordStatus.Read -> onOpen(row.record.id)
                            RecordStatus.Pending -> asking = row
                            RecordStatus.Available -> asking = row // the alert says so if another one is on its way
                        }
                    }
                }
            }
            if (rows.isEmpty()) Text(
                stringResource(R.string.records_none, query), Modifier.fillMaxWidth().padding(vertical = 20.dp),
                color = c.subText, fontSize = 15.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Text(stringResource(R.string.records_legal), Modifier.padding(start = 4.dp, end = 4.dp, top = 10.dp, bottom = 24.dp).navigationBarsPadding(), style = mono(10, c.subText).copy(lineHeight = 14.sp))
        }
    }

    asking?.let { row ->
        val pending = board.pending
        if (pending != null && pendingName != null) IosAlert(
            title = stringResource(R.string.record_busy_title),
            text = stringResource(R.string.record_busy, pendingName, caseTimeOf(pending.readyAt)),
            confirm = stringResource(R.string.ok), onDismiss = { asking = null },
        ) { asking = null }
        else IosAlert(
            title = stringResource(R.string.record_request_q),
            text = stringResource(R.string.record_request_text, row.record.name, board.hoursNow),
            confirm = stringResource(R.string.record_request_confirm), onDismiss = { asking = null },
        ) {
            asking = null
            onRequest(row.record.id)
        }
    }
}

@Composable
private fun RecordRowView(row: RecordRow, hours: Int, caseTimeOf: (Long) -> String, onClick: () -> Unit) {
    val c = LocalPolice.current
    val record = row.record
    val read = row.status == RecordStatus.Read
    Box(Modifier.fillMaxWidth().clickable(role = Role.Button, onClick = onClick)) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Thumbnail(record.vehicle, Modifier.size(48.dp, 56.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(record.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(record.subtitle, color = c.subText, fontSize = 13.sp)
                // What someone is (record, complainant...) is only known once their record has been read.
                if (read) Badge(record.badge, record.tone)
                when (row.status) {
                    RecordStatus.Available -> Text(stringResource(R.string.record_request, hours), color = c.tint, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                    RecordStatus.Pending -> Text(stringResource(R.string.record_pending, caseTimeOf(row.request!!.readyAt)), color = c.subText, fontSize = 13.sp)
                    RecordStatus.Ready -> Text(stringResource(R.string.record_ready), color = Kit.Accept, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    RecordStatus.Read -> {}
                }
            }
            if (row.status == RecordStatus.Ready || read) Icon(RukiaIcons.ChevronRight, null, Modifier.size(16.dp), tint = c.chevron)
        }
        // Investigated: it's on file and can be opened again whenever.
        if (read) ConfidentialStamp(Modifier.align(Alignment.CenterEnd).padding(end = 34.dp))
    }
}

/** The red, slanted CONFIDENTIAL stamp of a record the player has investigated. */
@Composable
private fun ConfidentialStamp(modifier: Modifier) {
    val red = Color(0xFFC62828)
    Text(
        stringResource(R.string.record_confidential),
        modifier.rotate(-12f).border(2.dp, red.copy(alpha = 0.85f), RoundedCornerShape(3.dp)).padding(2.dp)
            .border(1.dp, red.copy(alpha = 0.85f), RoundedCornerShape(2.dp)).padding(horizontal = 6.dp, vertical = 2.dp),
        style = mono(11, red.copy(alpha = 0.85f), FontWeight.SemiBold).copy(letterSpacing = 2.sp),
    )
}

/** A grey silhouette against a height chart, or a car. */
@Composable
private fun Thumbnail(vehicle: Boolean, modifier: Modifier) {
    Canvas(modifier.clip(RoundedCornerShape(6.dp))) {
        drawRect(Color(0xFFD7DBE0))
        val grey = if (vehicle) Color(0xFF8E96A3) else Color(0xFF9CA3AF)
        if (vehicle) {
            drawRect(grey, Offset(size.width * 0.08f, size.height * 0.38f), Size(size.width * 0.84f, size.height * 0.26f))
            drawCircle(Color(0xFF4B5563), size.width * 0.1f, Offset(size.width * 0.3f, size.height * 0.68f))
            drawCircle(Color(0xFF4B5563), size.width * 0.1f, Offset(size.width * 0.7f, size.height * 0.68f))
        } else {
            var y = 0f
            while (y < size.height) { drawRect(Color(0xFFCBD0D6), Offset(0f, y + 9.dp.toPx()), Size(size.width, 1.dp.toPx())); y += 10.dp.toPx() }
            drawCircle(grey, size.width * 0.18f, Offset(size.width / 2, size.height * 0.38f))
            drawOval(grey, Offset(size.width * 0.06f, size.height * 0.72f), Size(size.width * 0.88f, size.height * 0.6f))
        }
    }
}

/** A record that came in, in full. Tapping a linked record that came in too opens it ([onOpen]); its badge shows once it was read ([isRead]). */
@Composable
fun RecordScreen(caseTitle: String, opened: OpenedRecord, canOpen: (String) -> Boolean, isRead: (String) -> Boolean, onBack: () -> Unit, onOpen: (String) -> Unit) {
    BackHandler(onBack = onBack)
    val c = LocalPolice.current
    val record = opened.record
    val border = c.separator
    Column(
        Modifier.fillMaxSize().background(c.background).verticalScroll(rememberScrollState()).statusBarsPadding().navigationBarsPadding()
            .padding(start = 12.dp, end = 12.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(Modifier.heightIn(min = 44.dp).clickable(role = Role.Button, onClick = onBack), verticalAlignment = Alignment.CenterVertically) {
                Icon(RukiaIcons.ChevronLeft, null, Modifier.size(24.dp), tint = c.tint)
                Text(stringResource(R.string.records_back), color = c.tint, fontSize = 17.sp)
            }
            Spacer(Modifier.weight(1f))
            Text(record.reference, Modifier.padding(end = 8.dp), style = mono(11, c.subText, FontWeight.SemiBold))
        }
        Column(Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp)).background(c.card).border(1.dp, border, RoundedCornerShape(6.dp))) {
            if (record.banner != null) Column(Modifier.fillMaxWidth().background(Color(0xFFB3261E)).padding(horizontal = 14.dp, vertical = 10.dp)) {
                Text(record.banner, style = mono(12, Color.White, FontWeight.SemiBold))
                record.bannerNote?.let { Text(it, style = mono(9, Color.White.copy(alpha = 0.9f))) }
            } else Row(Modifier.fillMaxWidth().background(Color(0xFF1C2633)).padding(horizontal = 14.dp, vertical = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(stringResource(R.string.record_restricted), style = mono(10, Color.White))
                Text(stringResource(if (record.vehicle) R.string.record_vehicle else R.string.record_person), style = mono(10, Color.White))
            }
            Identity(opened)
            record.sections.forEachIndexed { i, section ->
                HorizontalDivider(thickness = 1.dp, color = border)
                SectionView("%02d · %s".format(i + 1, section.title), section, canOpen, isRead, onOpen)
            }
            Column(Modifier.fillMaxWidth().background(c.alert).padding(horizontal = 14.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(stringResource(R.string.record_logged, opened.consultedAt), style = mono(9, c.subText))
                Text(stringResource(R.string.record_reason, caseTitle), style = mono(9, c.subText))
            }
        }
        Text(stringResource(R.string.record_hint), Modifier.fillMaxWidth().padding(horizontal = 8.dp), color = c.subText, fontSize = 12.sp, lineHeight = 16.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}

/** Photos (police photos, the family's photo or a vehicle), name and the key facts. */
@Composable
private fun Identity(opened: OpenedRecord) {
    val c = LocalPolice.current
    val record = opened.record
    Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        val plate = record.mugshot
        if (plate != null) {
            listOf(false, true).forEach { profile ->
                val label = stringResource(if (profile) R.string.mugshot_profile else R.string.mugshot_front)
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    val path = "${ListMugshots.FOLDER}/${record.id}_${if (profile) "perfil" else "frontal"}.jpg"
                    MugshotPicture(plate, profile, path, label, Modifier.size(92.dp, 114.dp))
                    Text(label, style = mono(9, c.subText))
                }
            }
        } else if (record.vehicle) {
            Thumbnail(true, Modifier.size(92.dp, 92.dp))
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                val image = rememberMediaImage(record.photo)
                Box(Modifier.size(104.dp, 130.dp).border(4.dp, Color.White).border(1.dp, c.separator)) {
                    if (image != null) Image(image, record.photoCaption, Modifier.fillMaxSize().padding(4.dp), contentScale = ContentScale.Crop)
                    else Thumbnail(false, Modifier.fillMaxSize().padding(4.dp))
                }
                record.photoCaption?.let { Text(it, Modifier.width(104.dp), style = mono(8, c.subText)) }
            }
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            if (record.banner == null) Badge(record.badge, record.tone, 9)
            Text(record.name, fontSize = 20.sp, lineHeight = 23.sp, fontWeight = FontWeight.Bold)
            record.facts.forEach { Text(it, style = mono(11, c.preview)) }
            opened.missingFor?.let { millis ->
                Column(Modifier.padding(top = 4.dp)) {
                    Text(stringResource(R.string.missing_for), style = mono(9, c.subText))
                    val hours = millis / 3_600_000
                    Text(stringResource(R.string.days_hours, hours / 24, hours % 24), color = c.red, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SectionView(title: String, section: RecordSection, canOpen: (String) -> Boolean, isRead: (String) -> Boolean, onOpen: (String) -> Unit) {
    val c = LocalPolice.current
    Column(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = mono(11, c.text, FontWeight.SemiBold))
        if (section.fields.isNotEmpty()) FieldGrid(section)
        section.highlight?.let {
            Text(it, Modifier.fillMaxWidth().background(c.warnBackground).padding(horizontal = 10.dp, vertical = 8.dp), color = c.text, fontSize = 14.sp, lineHeight = 19.sp)
        }
        section.entries.forEach { e ->
            val bar = if (e.tone == "warn") Color(0xFFB45309) else Color(0xFF8E8E93)
            Column(
                Modifier.fillMaxWidth().border(1.dp, c.separator).drawBehind { drawRect(bar, size = size.copy(width = 3.dp.toPx())) }
                    .padding(start = 13.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(e.code, style = mono(10, c.subText))
                    Text(e.date, style = mono(10, c.subText))
                }
                Text(e.title, fontSize = 15.sp, lineHeight = 19.sp, fontWeight = FontWeight.SemiBold)
                Text(e.detail, color = c.preview, fontSize = 13.sp, lineHeight = 17.sp)
                Badge(e.state, if (e.tone == "warn") "police" else "clean", 9)
            }
        }
        section.timeline.forEachIndexed { i, event ->
            Row(Modifier.height(IntrinsicSize.Min), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                val dot = if (event.done) c.tint else c.chevron
                Column(Modifier.width(14.dp).fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(Modifier.padding(top = 4.dp).size(10.dp).background(dot, RoundedCornerShape(5.dp)))
                    if (i < section.timeline.lastIndex) Box(Modifier.weight(1f).width(2.dp).background(c.separator))
                }
                Column(Modifier.weight(1f).padding(bottom = 12.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(event.time, style = mono(10, c.subText))
                    Text(event.text, color = if (event.done) c.text else c.subText, fontSize = 14.sp, lineHeight = 18.sp)
                    Text(event.source, style = mono(9, c.subText))
                }
            }
        }
        section.fingerprints?.let { FingerprintsView(it) }
        section.links.forEach { link ->
            val target = link.record?.takeIf(canOpen)
            Row(
                Modifier.fillMaxWidth().border(1.dp, c.separator)
                    .then(if (target != null) Modifier.clickable(role = Role.Button) { onOpen(target) } else Modifier)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Column(Modifier.weight(1f)) {
                    Text(link.name, color = if (target != null) c.tint else c.text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text(link.role, color = c.preview, fontSize = 12.sp)
                }
                // A linked person's role only shows once their own record has been read.
                if (link.record == null || isRead(link.record)) Badge(link.badge, link.tone, 9)
            }
        }
        section.text?.let { Text(it, fontSize = 14.sp, lineHeight = 20.sp) }
        section.signature?.let { Text(it, style = mono(9, c.subText)) }
    }
}

/** Two columns; a wide field takes the whole row. */
@Composable
private fun FieldGrid(section: RecordSection) {
    val c = LocalPolice.current
    val rows = mutableListOf<List<com.rukia.police.domain.model.RecordField>>()
    var pair = mutableListOf<com.rukia.police.domain.model.RecordField>()
    section.fields.forEach { f ->
        if (f.wide) { if (pair.isNotEmpty()) rows += pair; rows += listOf(f); pair = mutableListOf() }
        else { pair += f; if (pair.size == 2) { rows += pair; pair = mutableListOf() } }
    }
    if (pair.isNotEmpty()) rows += pair
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.forEach { f ->
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Text(f.label, style = mono(9, c.subText))
                        if (f.mono) Text(f.value, style = mono(13, c.text)) else Text(f.value, fontSize = 14.sp, lineHeight = 19.sp)
                    }
                }
                if (row.size == 1 && !row[0].wide) Spacer(Modifier.weight(1f))
            }
        }
    }
}

/** Ten fingers drawn by their Olóriz code, the formula, and what the comparison found. */
@Composable
private fun FingerprintsView(prints: Fingerprints) {
    val c = LocalPolice.current
    val names = stringArrayResource(R.array.finger_names)
    Text(prints.taken, style = mono(9, c.subText))
    listOf(R.string.right_hand to prints.right, R.string.left_hand to prints.left).forEach { (hand, codes) ->
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(stringResource(hand), style = mono(9, c.subText, FontWeight.SemiBold))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                codes.take(5).forEachIndexed { i, code ->
                    Column(
                        Modifier.weight(1f).border(1.dp, c.separator).background(c.alert).padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        Fingerprint(code, c.text, Modifier.size(40.dp, 50.dp).clip(RoundedCornerShape(topStartPercent = 45, topEndPercent = 45, bottomStartPercent = 40, bottomEndPercent = 40)))
                        Text(names.getOrElse(i) { "" }, style = mono(7, c.subText), maxLines = 1)
                        Text(code.toString(), style = mono(11, c.text, FontWeight.SemiBold))
                    }
                }
            }
        }
    }
    Column(Modifier.fillMaxWidth().background(c.alert).border(1.dp, c.separator).padding(horizontal = 10.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(stringResource(R.string.fingerprint_formula), style = mono(9, c.subText))
        Text(prints.formula, style = mono(16, c.text, FontWeight.SemiBold))
        Text(prints.note, color = c.preview, fontSize = 12.sp, lineHeight = 16.sp)
    }
}

/** Ridges by pattern: whorl (V, 4), arch (A, 1), or a loop to one side (E, 3) or the other. */
@Composable
private fun Fingerprint(code: Char, color: Color, modifier: Modifier) {
    Canvas(modifier) {
        val stroke = Stroke(1.dp.toPx())
        val ink = color.copy(alpha = 0.75f)
        scale(size.width / 48f, size.height / 60f, Offset.Zero) {
            val path = Path()
            when (code) {
                'V', '4' -> for (r in 3..21 step 3) path.addOval(androidx.compose.ui.geometry.Rect(24f - r, 30f - r * 1.25f, 24f + r, 30f + r * 1.25f))
                'A', '1' -> for (y in 10..62 step 4) { path.moveTo(0f, y.toFloat()); path.quadraticTo(24f, y - 16f, 48f, y.toFloat()) }
                else -> {
                    for (r in 3..15 step 3) {
                        path.moveTo(20f - r, 60f); path.lineTo(20f - r, 32f)
                        path.arcTo(androidx.compose.ui.geometry.Rect(20f - r, 32f - r, 20f + r, 32f + r), 180f, 180f, false)
                        path.lineTo(20f + r, 60f)
                    }
                    for (y in 6..18 step 4) { path.moveTo(0f, y + 10f); path.quadraticTo(24f, y - 12f, 48f, y + 14f) }
                }
            }
            if (code == 'E' || code == '3') scale(-1f, 1f, Offset(24f, 30f)) { drawPath(path, ink, style = stroke) }
            else drawPath(path, ink, style = stroke)
        }
    }
}
