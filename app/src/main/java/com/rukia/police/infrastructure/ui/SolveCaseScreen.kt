package com.rukia.police.infrastructure.ui

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Point
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.rukia.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.Role
import com.rukia.game.infrastructure.ui.Barlow
import com.rukia.game.infrastructure.ui.CutCorners
import com.rukia.game.infrastructure.ui.Rajdhani
import com.rukia.game.infrastructure.ui.Term
import com.rukia.game.infrastructure.ui.TermDialog
import com.rukia.game.infrastructure.ui.mono
import com.rukia.game.infrastructure.ui.terminalBackdrop
import com.rukia.phone.infrastructure.ui.RukiaIcons
import com.rukia.phone.infrastructure.ui.SystemBars
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.rukia.police.domain.model.CaseQuestion
import com.rukia.police.domain.model.Place
import com.rukia.police.domain.model.Search
import com.rukia.police.domain.model.SearchBoard
import com.rukia.police.domain.model.Spot
import kotlinx.coroutines.delay
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.CopyrightOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.Polygon
import org.osmdroid.views.overlay.TilesOverlay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.pow
import kotlin.math.sin

private val CATALONIA = BoundingBox(42.9, 3.4, 40.45, 0.1)
private const val MAX_RADIUS = 30_000.0
private val Miss = Color(0xFF8FA3B5)
private val Hit = Color(0xFFFF9F1C)
private val Found = Color(0xFF7FD9A0)
private val PlaceColor = Color(0xFFF2B33D)

/** A tap this close to a place's pin (in dp) puts the zone right on it. */
private const val SNAP_DP = 28

/**
 * The map of Catalonia. Each day the player taps where the zone goes, sizes it, and sends a squad: the cars drive from
 * the nearest police station and search it, then its accuracy comes in. [now] is the case's clock.
 */
@Composable
fun SolveCaseScreen(
    caseTitle: String,
    question: CaseQuestion,
    /** The case's places the player knows of: marked on the map and suggested by the search box. */
    places: List<Place>,
    board: SearchBoard, now: () -> Long, onBack: () -> Unit,
    /** Where a place name is, or null; blocking, so it runs off the main thread. */
    onFind: (String) -> Spot?,
    onSearch: (Spot, Double) -> Unit,
) {
    BackHandler(onBack = onBack)
    SystemBars(lightBottomIcons = true)
    var focus by remember { mutableStateOf<Focus?>(null) }
    var query by rememberSaveable { mutableStateOf("") }
    var notFound by remember { mutableStateOf(false) }
    var looking by remember { mutableStateOf(false) }
    var typing by remember { mutableStateOf(false) }
    var draft by remember { mutableStateOf<Spot?>(null) }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    // Shows [spot] and puts the zone there, ready to size and send.
    val goTo = { spot: Spot -> focus = Focus(spot); draft = spot }
    val pick = { place: Place -> query = place.name; notFound = false; focusManager.clearFocus(); goTo(place.spot) }
    val find = {
        val known = places.firstOrNull { query.isNotBlank() && it.matches(query) }
        if (known != null) pick(known)
        else if (query.isNotBlank() && !looking) {
            focusManager.clearFocus()
            looking = true
            scope.launch {
                val spot = withContext(Dispatchers.IO) { onFind(query) }
                notFound = spot == null
                if (spot != null) goTo(spot)
                looking = false
            }
        }
    }
    // 0..1 on a log scale, so small zones get as much of the slider as big ones.
    var size by rememberSaveable { mutableFloatStateOf(0.5f) }
    val radius = question.tolerance * (MAX_RADIUS / question.tolerance).pow(size.toDouble())
    var confirming by remember { mutableStateOf(false) }
    // Ticks every second for the countdown; the map animates the cars on its own.
    val time by produceState(now()) { while (true) { value = now(); delay(1_000) } }
    val pending = board.pending(time)
    val last = board.searches.lastOrNull()?.takeIf { it.day == board.today && pending == null }

    Column(Modifier.fillMaxSize().background(Term.Ground)) {
        Row(
            Modifier.fillMaxWidth().background(Color(0xFF0B121B)).drawBehind {
                drawLine(Term.Cyan.copy(alpha = 0.3f), Offset(0f, this.size.height), Offset(this.size.width, this.size.height), 1.dp.toPx())
            }.statusBarsPadding().padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(Modifier.size(40.dp).border(1.dp, Term.Cyan.copy(alpha = 0.35f)).clickable(role = Role.Button, onClick = onBack), contentAlignment = Alignment.Center) {
                Icon(RukiaIcons.ArrowLeft, stringResource(R.string.back), Modifier.size(20.dp), tint = Term.Text)
            }
            Column(Modifier.weight(1f)) {
                Text(stringResource(R.string.solve_kicker, caseTitle), style = mono(10, Term.Cyan, spacing = 1.5f))
                Text(stringResource(R.string.solve_case), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 23.sp, lineHeight = 25.sp, color = Term.Text)
            }
            Text(
                stringResource(R.string.solve_day, board.today, board.lastDay ?: board.today),
                Modifier.border(1.dp, Term.Amber).padding(horizontal = 8.dp, vertical = 4.dp), style = mono(11, Term.Amber, FontWeight.SemiBold, 0f),
            )
        }
        Box(Modifier.weight(1f).clipToBounds()) {
            SearchMap(question, places, board.searches, draft?.takeIf { board.canSearch }, radius, now, focus, Modifier.fillMaxSize()) { if (board.canSearch) draft = it }
            Column(
                Modifier.padding(10.dp).fillMaxWidth().background(Color(0xE0070B12)).border(1.dp, Term.Cyan.copy(alpha = 0.4f)).padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
            ) {
                Text(stringResource(R.string.solve_objective), style = mono(10, Term.Amber, spacing = 1.5f))
                Text(question.question, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 21.sp, color = Term.Text)
                Text(stringResource(R.string.map_hint), fontFamily = Barlow, fontSize = 12.sp, lineHeight = 16.sp, color = Term.Muted)
                OutlinedTextField(
                    query, { query = it; notFound = false }, Modifier.fillMaxWidth().padding(top = 6.dp).onFocusChanged { typing = it.isFocused },
                    placeholder = { Text(stringResource(R.string.search_place), color = Term.Muted) },
                    singleLine = true, isError = notFound,
                    supportingText = if (notFound) ({ Text(stringResource(R.string.place_not_found)) }) else null,
                    trailingIcon = {
                        if (looking) CircularProgressIndicator(Modifier.size(20.dp), color = Term.Cyan, strokeWidth = 2.dp)
                        else IconButton(find) { Icon(RukiaIcons.Search, stringResource(R.string.search_place), tint = Term.Cyan) }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { find() }),
                    shape = RoundedCornerShape(0.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Term.Text, unfocusedTextColor = Term.Text, cursorColor = Term.Cyan,
                        focusedBorderColor = Term.Cyan, unfocusedBorderColor = Term.Cyan.copy(alpha = 0.35f),
                        focusedContainerColor = Color(0xFF0C1620), unfocusedContainerColor = Color(0xFF0C1620),
                    ),
                )
                // The places the case points to, while typing: one tap and the zone is there.
                val suggested = places.filter { it.matches(query) }
                if (typing && suggested.isNotEmpty()) {
                    Text(stringResource(R.string.case_places).uppercase(), Modifier.padding(top = 6.dp), style = mono(10, Term.Muted, FontWeight.SemiBold, 1f))
                    Column(Modifier.heightIn(max = 220.dp).verticalScroll(rememberScrollState())) {
                        for (place in suggested) Row(
                            Modifier.fillMaxWidth().clickable { pick(place) }.padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Box(Modifier.size(10.dp).background(PlaceColor))
                            Column {
                                Text(place.name, fontFamily = Barlow, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Term.Text)
                                if (place.note.isNotEmpty()) Text(place.note, fontFamily = Barlow, fontSize = 13.sp, lineHeight = 16.sp, color = Term.Muted)
                            }
                        }
                    }
                }
            }
        }
        Column(
            Modifier.fillMaxWidth().background(Color(0xFF0C1620)).drawBehind { drawLine(Term.Cyan, Offset.Zero, Offset(this.size.width, 0f), 1.dp.toPx()) }
                .navigationBarsPadding().padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            DayStrip(board, pending)
            when {
                pending != null -> Notice(Term.Cyan, "…", stringResource(R.string.squad_searching), stringResource(R.string.result_in, countdown(pending.readyAt - time)))
                last != null && last.accuracy == 0 -> Notice(Miss, "✕", stringResource(R.string.zone_miss_title), stringResource(R.string.zone_miss_text))
                last != null -> Notice(Hit, "${last.accuracy}%", stringResource(R.string.zone_hit_title, last.accuracy), stringResource(R.string.zone_hit_text))
                !board.canSearch -> Text(stringResource(R.string.solve_closed), fontFamily = Barlow, fontSize = 15.sp, color = Term.Muted)
                else -> {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(stringResource(R.string.radius).uppercase(), Modifier.width(52.dp), style = mono(11, Term.Muted, FontWeight.SemiBold, 1f))
                        Slider(
                            size, { size = it }, Modifier.weight(1f),
                            colors = SliderDefaults.colors(thumbColor = Term.Cyan, activeTrackColor = Term.Cyan, inactiveTrackColor = Term.Cyan.copy(alpha = 0.2f)),
                        )
                        Text(
                            if (radius < 1000) "%d m".format(radius.toInt()) else "%.1f km".format(radius / 1000), Modifier.width(64.dp),
                            style = mono(14, if (radius <= question.tolerance * 1.01) Found else Term.Text, FontWeight.SemiBold, 0f).copy(textAlign = TextAlign.End),
                        )
                    }
                    Box(
                        Modifier.fillMaxWidth().heightIn(min = 54.dp).clip(CutCorners).background(if (draft != null) Term.Amber else Term.Muted.copy(alpha = 0.15f))
                            .clickable(enabled = draft != null, role = Role.Button) { confirming = true },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            (if (draft != null) stringResource(R.string.send_squad) + " ›" else stringResource(R.string.place_zone_first)).uppercase(),
                            fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 2.sp,
                            color = if (draft != null) Term.Ink else Term.Muted,
                        )
                    }
                }
            }
        }
    }

    if (confirming) TermDialog(
        stringResource(R.string.solve_kicker, caseTitle), stringResource(R.string.send_squad_q), stringResource(R.string.send_squad_text),
        stringResource(R.string.send).uppercase(), onCancel = { confirming = false },
        onConfirm = {
            confirming = false
            draft?.let { onSearch(it, radius) }
            draft = null
        },
    )
}

/** A request to show [spot]; not a data class, so searching the same place again moves the map back to it. */
private class Focus(val spot: Spot)

private fun countdown(millis: Long) = (millis.coerceAtLeast(0) / 1000).let { "%d:%02d".format(it / 60, it % 60) }

/** One cell per case day up to the deadline: what that day's search found. */
@Composable
private fun DayStrip(board: SearchBoard, pending: Search?) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        for (n in 1..maxOf(board.lastDay ?: 0, board.today, 1)) {
            val s = board.searches.find { it.day == n }
            val (color, value) = when {
                s == null -> (if (n == board.today) Term.Cyan else Term.Dim) to (if (n == board.today) stringResource(R.string.today).uppercase() else "·")
                s == pending -> Term.Cyan to "…"
                s.accuracy == 0 -> Miss to "✕"
                else -> (if (s.accuracy == 100) Found else Hit) to "${s.accuracy}%"
            }
            val lit = s != null || n == board.today
            Column(
                Modifier.weight(1f).height(40.dp).background(color.copy(alpha = if (lit) 0.12f else 0.06f)).border(1.dp, if (lit) color else Term.Muted.copy(alpha = 0.15f)),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
            ) {
                Text("D$n", style = mono(10, color, FontWeight.SemiBold, 0f))
                Text(value, style = mono(10, color, spacing = 0f), maxLines = 1)
            }
        }
    }
}

@Composable
private fun Notice(color: Color, badge: String, title: String, text: String) {
    Row(Modifier.fillMaxWidth().background(color.copy(alpha = 0.08f)).border(1.dp, color).padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.defaultMinSize(minWidth = 44.dp).height(44.dp).border(2.dp, color).padding(horizontal = 4.dp), contentAlignment = Alignment.Center) {
            Text(badge, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 17.sp, color = color)
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
            Text(text, fontFamily = Barlow, fontSize = 14.sp, lineHeight = 19.sp, color = Term.Body)
        }
    }
}

/** OpenStreetMap of Catalonia with the past zones, the [draft] one, the station and the cars of the search under way. */
@Composable
private fun SearchMap(question: CaseQuestion, places: List<Place>, searches: List<Search>, draft: Spot?, radius: Double, now: () -> Long, focus: Focus?, modifier: Modifier, onTap: (Spot) -> Unit) {
    val context = LocalContext.current
    val tint = Term.Cyan
    val stationLabel = stringResource(R.string.station)
    val map = remember {
        Configuration.getInstance().apply {
            load(context, context.getSharedPreferences("osmdroid", 0))
            userAgentValue = context.packageName // OSM's tile servers refuse requests without one
        }
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            // Tiles at the screen's density: the map's own names are readable on a phone.
            isTilesScaledToDpi = true
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setScrollableAreaLimitDouble(CATALONIA)
            minZoomLevel = 7.0
            maxZoomLevel = 18.0
            controller.setZoom(8.0)
            controller.setCenter(GeoPoint(41.75, 1.75))
            // Opens on the case: the station and the places known so far, with room on top for the question's card.
            val spots = (places.map { it.spot } + question.station).map { GeoPoint(it.lat, it.lon) }
            addOnFirstLayoutListener { _, _, _, _, _ ->
                zoomToBoundingBox(roomForCard(BoundingBox.fromGeoPoints(spots).increaseByScale(1.3f)), false, (32 * resources.displayMetrics.density).toInt(), 13.0, null)
            }
        }
    }
    DisposableEffect(map) { onDispose { map.onDetach() } }
    // Zooms in on a searched place, with the whole zone around it in view.
    LaunchedEffect(focus) {
        focus?.let {
            val dLat = radius / 111_320
            val dLon = radius / (111_320 * cos(Math.toRadians(it.spot.lat)))
            map.zoomToBoundingBox(roomForCard(BoundingBox(it.spot.lat + dLat, it.spot.lon + dLon, it.spot.lat - dLat, it.spot.lon - dLon)), true, (32 * map.resources.displayMetrics.density).toInt(), 15.0, 800L)
        }
    }
    val pending = searches.lastOrNull()?.takeIf { now() < it.readyAt }
    // Moves the cars: redraws the map while a squad is out.
    LaunchedEffect(pending) { while (pending != null && now() < pending.readyAt) { map.invalidate(); delay(50) } }

    AndroidView({ map }, modifier) { m ->
        m.overlayManager.tilesOverlay.setColorFilter(TilesOverlay.INVERT_COLORS)
        m.overlays.clear()
        for (s in searches) {
            val color = when { s == pending -> tint; s.accuracy == 0 -> Miss; s.accuracy == 100 -> Found; else -> Hit }
            m.overlays += zone(s.center, s.radius, color, m.resources.displayMetrics.density, dashed = s.accuracy == 0 && s != pending)
        }
        draft?.let { m.overlays += zone(it, radius, tint, m.resources.displayMetrics.density) }
        val density = m.resources.displayMetrics.density
        m.overlays += PlacesOverlay(places, density)
        m.overlays += SquadOverlay(question.allStations, pending, now, density, tint.toArgb(), stationLabel)
        m.overlays += CopyrightOverlay(m.context)
        // Last, so it gets the taps first. A tap on a place's pin puts the zone right on the place.
        m.overlays += MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                val tap = m.projection.toPixels(p, null)
                val pixels = { s: Spot -> m.projection.toPixels(GeoPoint(s.lat, s.lon), null).let { hypot((it.x - tap.x).toDouble(), (it.y - tap.y).toDouble()) } }
                val near = places.map { it.spot }.minByOrNull(pixels)?.takeIf { pixels(it) <= SNAP_DP * density }
                onTap(near ?: Spot(p.latitude, p.longitude))
                return true
            }
            override fun longPressHelper(p: GeoPoint) = false
        })
        m.invalidate()
    }
}

/** [box] grown northwards, so what's in it isn't hidden under the question's card at the top of the map. */
private fun roomForCard(box: BoundingBox) = BoundingBox(box.latNorth + box.latitudeSpan * 0.6, box.lonEast, box.latSouth, box.lonWest)

private fun zone(center: Spot, radius: Double, color: Color, density: Float, dashed: Boolean = false) = Polygon().apply {
    points = Polygon.pointsAsCircle(GeoPoint(center.lat, center.lon), radius)
    outlinePaint.color = color.toArgb()
    outlinePaint.strokeWidth = 2 * density
    if (dashed) outlinePaint.pathEffect = DashPathEffect(floatArrayOf(6 * density, 4 * density), 0f)
    fillPaint.color = color.copy(alpha = 0.2f).toArgb()
}

/** The case's places: a big pin each, with its name on a pill so it reads over the map at any zoom. */
private class PlacesOverlay(private val places: List<Place>, private val density: Float) : Overlay() {
    private val pin = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = PlaceColor.toArgb() }
    private val ring = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = android.graphics.Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 3 * density }
    private val pill = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = PlaceColor.toArgb() }
    private val text = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Term.Ink.toArgb(); textSize = 13 * density; isFakeBoldText = true }
    private val p = Point()

    override fun draw(canvas: Canvas, projection: Projection) {
        for (place in places) {
            projection.toPixels(GeoPoint(place.spot.lat, place.spot.lon), p)
            val x = p.x.toFloat(); val y = p.y.toFloat()
            canvas.drawCircle(x, y, 10 * density, pin)
            canvas.drawCircle(x, y, 10 * density, ring)
            val w = text.measureText(place.name)
            val left = x - w / 2 - 8 * density; val top = y - 40 * density
            canvas.drawRoundRect(left, top, left + w + 16 * density, top + 22 * density, 11 * density, 11 * density, pill)
            canvas.drawText(place.name, x - w / 2, top + 16 * density, text)
        }
    }
}

/**
 * The stations, and the [search]'s cars: each leaves the nearest one a moment after the last, drives there in the first
 * [Search.driveShare] of the search, then circles inside the zone until the result comes in.
 */
private class SquadOverlay(
    private val stations: List<Spot>, private val search: Search?, private val now: () -> Long,
    private val density: Float, private val tint: Int, private val label: String,
) : Overlay() {
    private val fill = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = tint }
    private val ring = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = android.graphics.Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 2.5f * density }
    private val text = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; textSize = 11 * density; isFakeBoldText = true }
    private val car = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; textSize = 22 * density }
    private val p = Point()

    override fun draw(canvas: Canvas, projection: Projection) {
        // The names only fit once the map is zoomed in a bit.
        val named = projection.zoomLevel >= 9
        for (station in stations) {
            projection.toPixels(GeoPoint(station.lat, station.lon), p)
            canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), 7 * density, fill)
            canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), 7 * density, ring)
            if (!named) continue
            text.color = android.graphics.Color.WHITE; text.style = Paint.Style.STROKE; text.strokeWidth = 3 * density
            canvas.drawText(label, p.x.toFloat(), p.y + 22 * density, text)
            text.color = tint; text.style = Paint.Style.FILL
            canvas.drawText(label, p.x.toFloat(), p.y + 22 * density, text)
        }
        val s = search ?: return
        val t = (now() - s.at).toDouble() / s.duration
        val from = s.from ?: stations.first()
        for (i in 0 until CARS) {
            val at = carAt(from, s, t - i * 0.02, i)
            projection.toPixels(GeoPoint(at.lat, at.lon), p)
            canvas.drawText("🚔", p.x.toFloat(), p.y + 8 * density, car) // 🚔
        }
    }

    companion object {
        const val CARS = 3
    }
}

/** Where car [i] of [s] is at [t] (0..1 of the search): on its way, then circling in the zone. */
private fun carAt(station: Spot, s: Search, t: Double, i: Int): Spot {
    val orbit = { u: Double ->
        val a = 2 * PI * (u * 5 + i.toDouble() / SquadOverlay.CARS)
        val r = s.radius * 0.6
        Spot(s.center.lat + r * cos(a) / 111_320, s.center.lon + r * sin(a) / (111_320 * cos(Math.toRadians(s.center.lat))))
    }
    if (t >= s.driveShare) return orbit(t - s.driveShare)
    val k = (t / s.driveShare).coerceIn(0.0, 1.0).let { it * it * (3 - 2 * it) } // ease in and out
    val to = orbit(0.0)
    return Spot(station.lat + (to.lat - station.lat) * k, station.lon + (to.lon - station.lon) * k)
}

/** Once a squad confirms the place. The case goes on until the story's ending, which closes it: back returns to the phone. */
@Composable
fun VerdictScreen(caseTitle: String, onReturnToTitle: () -> Unit) {
    SystemBars(lightBottomIcons = true)
    Column(
        Modifier.fillMaxSize().terminalBackdrop().safeDrawingPadding().padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
    ) {
        Text(stringResource(R.string.case_file, caseTitle), style = mono(11, Term.Muted, spacing = 2f))
        Text(
            stringResource(R.string.case_solved),
            Modifier.rotate(-6f).border(4.dp, Found).padding(3.dp).border(1.dp, Found).padding(horizontal = 16.dp, vertical = 10.dp),
            style = mono(26, Found, FontWeight.SemiBold, 3f).copy(lineHeight = 30.sp, textAlign = TextAlign.Center),
        )
        Text(stringResource(R.string.solved_text), fontFamily = Barlow, fontSize = 17.sp, lineHeight = 24.sp, color = Color(0xFFD3DCE4), textAlign = TextAlign.Center)
        Box(
            Modifier.padding(top = 14.dp).heightIn(min = 52.dp).clip(CutCorners).background(Term.Amber).clickable(role = Role.Button, onClick = onReturnToTitle)
                .padding(horizontal = 26.dp),
            contentAlignment = Alignment.Center,
        ) { Text(stringResource(R.string.return_title).uppercase(), fontFamily = Rajdhani, fontWeight = FontWeight.Bold, fontSize = 18.sp, letterSpacing = 2.sp, color = Term.Ink) }
    }
}
