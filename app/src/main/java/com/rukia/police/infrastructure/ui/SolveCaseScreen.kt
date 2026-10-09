package com.rukia.police.infrastructure.ui

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Point
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.rukia.phone.infrastructure.ui.Glow
import com.rukia.phone.infrastructure.ui.SystemBars
import com.rukia.phone.infrastructure.ui.glow
import com.rukia.police.domain.model.CaseQuestion
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
import kotlin.math.pow
import kotlin.math.sin

private val CATALONIA = BoundingBox(42.9, 3.4, 40.45, 0.1)
private const val MAX_RADIUS = 30_000.0
private val Miss = Color(0xFF8E8E93)
private val Hit = Color(0xFFFF9500)
private val Found = Color(0xFF34C759)

/**
 * The map of Catalonia. Each day the player taps where the zone goes, sizes it, and sends a squad: the cars drive from
 * the station and search it for [Search.DURATION], then its accuracy comes in. [now] is the case's clock.
 */
@Composable
fun SolveCaseScreen(question: CaseQuestion, board: SearchBoard, now: () -> Long, onBack: () -> Unit, onSearch: (Spot, Double) -> Unit) {
    BackHandler(onBack = onBack)
    val c = LocalPolice.current
    var draft by remember { mutableStateOf<Spot?>(null) }
    // 0..1 on a log scale, so small zones get as much of the slider as big ones.
    var size by rememberSaveable { mutableFloatStateOf(0.5f) }
    val radius = question.tolerance * (MAX_RADIUS / question.tolerance).pow(size.toDouble())
    var confirming by remember { mutableStateOf(false) }
    // Ticks every second for the countdown; the map animates the cars on its own.
    val time by produceState(now()) { while (true) { value = now(); delay(1_000) } }
    val pending = board.pending(time)
    val last = board.searches.lastOrNull()?.takeIf { it.day == board.today && pending == null }

    Column(Modifier.fillMaxSize().background(c.background)) {
        SubHeader(stringResource(R.string.solve_case), onBack)
        Box(Modifier.weight(1f)) {
            SearchMap(question, board.searches, draft?.takeIf { board.canSearch }, radius, now, Modifier.fillMaxSize()) { if (board.canSearch) draft = it }
            Column(
                Modifier.padding(12.dp).fillMaxWidth().background(c.card.copy(alpha = 0.95f), RoundedCornerShape(16.dp)).padding(horizontal = 14.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(question.question, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Text(stringResource(R.string.map_hint), color = c.subText, fontSize = 13.sp, lineHeight = 17.sp)
            }
        }
        Column(Modifier.fillMaxWidth().background(c.card).navigationBarsPadding().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            DayStrip(board, pending)
            when {
                pending != null -> Notice(Color(0xFF1A63F0), "…", stringResource(R.string.squad_searching), stringResource(R.string.result_in, countdown(pending.readyAt - time)))
                last != null && last.accuracy == 0 -> Notice(Miss, "✕", stringResource(R.string.zone_miss_title), stringResource(R.string.zone_miss_text))
                last != null -> Notice(Hit, "${last.accuracy}%", stringResource(R.string.zone_hit_title, last.accuracy), stringResource(R.string.zone_hit_text))
                !board.canSearch -> Text(stringResource(R.string.solve_closed), color = c.subText, fontSize = 15.sp)
                else -> {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(stringResource(R.string.radius), fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        Slider(size, { size = it }, Modifier.weight(1f))
                        Text("%.1f km".format(radius / 1000), Modifier.width(64.dp), fontSize = 15.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End)
                    }
                    Button(
                        { confirming = true }, Modifier.fillMaxWidth().height(52.dp), enabled = draft != null, shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = c.tint, disabledContainerColor = c.ctaOffBackground, disabledContentColor = c.ctaOffText),
                    ) { Text(stringResource(if (draft != null) R.string.send_squad else R.string.place_zone_first), fontSize = 17.sp, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }

    if (confirming) IosAlert(stringResource(R.string.send_squad_q), stringResource(R.string.send_squad_text), stringResource(R.string.send), onDismiss = { confirming = false }) {
        confirming = false
        draft?.let { onSearch(it, radius) }
        draft = null
    }
}

private fun countdown(millis: Long) = (millis.coerceAtLeast(0) / 1000).let { "%d:%02d".format(it / 60, it % 60) }

/** One cell per case day up to the deadline: what that day's search found. */
@Composable
private fun DayStrip(board: SearchBoard, pending: Search?) {
    val c = LocalPolice.current
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        for (n in 1..maxOf(board.lastDay ?: 0, board.today, 1)) {
            val s = board.searches.find { it.day == n }
            val (color, value) = when {
                s == null -> (if (n == board.today) c.tint else c.subText) to (if (n == board.today) stringResource(R.string.today) else "·")
                s == pending -> c.tint to "…"
                s.accuracy == 0 -> Miss to "✕"
                else -> (if (s.accuracy == 100) Found else Hit) to "${s.accuracy}%"
            }
            Column(
                Modifier.weight(1f).height(44.dp).background(color.copy(alpha = 0.14f), RoundedCornerShape(10.dp))
                    .then(if (n == board.today) Modifier.border(1.5.dp, color, RoundedCornerShape(10.dp)) else Modifier),
                horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
            ) {
                Text("D$n", color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(value, color = color, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
            }
        }
    }
}

@Composable
private fun Notice(color: Color, badge: String, title: String, text: String) {
    Row(Modifier.fillMaxWidth().background(color.copy(alpha = 0.12f), RoundedCornerShape(14.dp)).padding(horizontal = 14.dp, vertical = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Box(Modifier.size(40.dp).background(color, RoundedCornerShape(20.dp)), contentAlignment = Alignment.Center) {
            Text(badge, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold)
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text, color = LocalPolice.current.subText, fontSize = 14.sp, lineHeight = 19.sp)
        }
    }
}

/** OpenStreetMap of Catalonia with the past zones, the [draft] one, the station and the cars of the search under way. */
@Composable
private fun SearchMap(question: CaseQuestion, searches: List<Search>, draft: Spot?, radius: Double, now: () -> Long, modifier: Modifier, onTap: (Spot) -> Unit) {
    val context = LocalContext.current
    val dark = isSystemInDarkTheme()
    val tint = LocalPolice.current.tint
    val stationLabel = stringResource(R.string.station)
    val map = remember {
        Configuration.getInstance().apply {
            load(context, context.getSharedPreferences("osmdroid", 0))
            userAgentValue = context.packageName // OSM's tile servers refuse requests without one
        }
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            setScrollableAreaLimitDouble(CATALONIA)
            minZoomLevel = 7.0
            maxZoomLevel = 18.0
            controller.setZoom(8.0)
            controller.setCenter(GeoPoint(41.75, 1.75))
        }
    }
    DisposableEffect(map) { onDispose { map.onDetach() } }
    val pending = searches.lastOrNull()?.takeIf { now() < it.readyAt }
    // Moves the cars: redraws the map while a squad is out.
    LaunchedEffect(pending) { while (pending != null && now() < pending.readyAt) { map.invalidate(); delay(50) } }

    AndroidView({ map }, modifier) { m ->
        m.overlayManager.tilesOverlay.setColorFilter(if (dark) TilesOverlay.INVERT_COLORS else null)
        m.overlays.clear()
        for (s in searches) {
            val color = when { s == pending -> tint; s.accuracy == 0 -> Miss; s.accuracy == 100 -> Found; else -> Hit }
            m.overlays += zone(s.center, s.radius, color, m.resources.displayMetrics.density, dashed = s.accuracy == 0 && s != pending)
        }
        draft?.let { m.overlays += zone(it, radius, tint, m.resources.displayMetrics.density) }
        m.overlays += SquadOverlay(question.station, pending, now, m.resources.displayMetrics.density, tint.toArgb(), stationLabel)
        m.overlays += CopyrightOverlay(m.context)
        // Last, so it gets the taps first.
        m.overlays += MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint): Boolean { onTap(Spot(p.latitude, p.longitude)); return true }
            override fun longPressHelper(p: GeoPoint) = false
        })
        m.invalidate()
    }
}

private fun zone(center: Spot, radius: Double, color: Color, density: Float, dashed: Boolean = false) = Polygon().apply {
    points = Polygon.pointsAsCircle(GeoPoint(center.lat, center.lon), radius)
    outlinePaint.color = color.toArgb()
    outlinePaint.strokeWidth = 2 * density
    if (dashed) outlinePaint.pathEffect = DashPathEffect(floatArrayOf(6 * density, 4 * density), 0f)
    fillPaint.color = color.copy(alpha = 0.2f).toArgb()
}

/**
 * The station, and the [search]'s cars: each leaves a moment after the last, drives there in the first [DRIVE] of the
 * search, then circles inside the zone until the result comes in.
 */
private class SquadOverlay(
    private val station: Spot, private val search: Search?, private val now: () -> Long,
    private val density: Float, private val tint: Int, private val label: String,
) : Overlay() {
    private val fill = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = tint }
    private val ring = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = android.graphics.Color.WHITE; style = Paint.Style.STROKE; strokeWidth = 2.5f * density }
    private val text = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; textSize = 11 * density; isFakeBoldText = true }
    private val car = Paint(Paint.ANTI_ALIAS_FLAG).apply { textAlign = Paint.Align.CENTER; textSize = 22 * density }
    private val p = Point()

    override fun draw(canvas: Canvas, projection: Projection) {
        projection.toPixels(GeoPoint(station.lat, station.lon), p)
        canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), 8 * density, fill)
        canvas.drawCircle(p.x.toFloat(), p.y.toFloat(), 8 * density, ring)
        text.color = android.graphics.Color.WHITE; text.style = Paint.Style.STROKE; text.strokeWidth = 3 * density
        canvas.drawText(label, p.x.toFloat(), p.y + 22 * density, text)
        text.color = tint; text.style = Paint.Style.FILL
        canvas.drawText(label, p.x.toFloat(), p.y + 22 * density, text)
        val s = search ?: return
        val t = (now() - s.at).toDouble() / Search.DURATION
        for (i in 0 until CARS) {
            val at = carAt(station, s, t - i * 0.02, i)
            projection.toPixels(GeoPoint(at.lat, at.lon), p)
            canvas.drawText("🚔", p.x.toFloat(), p.y + 8 * density, car) // 🚔
        }
    }

    companion object {
        const val CARS = 3
        const val DRIVE = 0.4
    }
}

/** Where car [i] of [s] is at [t] (0..1 of the search): on its way, then circling in the zone. */
private fun carAt(station: Spot, s: Search, t: Double, i: Int): Spot {
    val orbit = { u: Double ->
        val a = 2 * PI * (u * 5 + i.toDouble() / SquadOverlay.CARS)
        val r = s.radius * 0.6
        Spot(s.center.lat + r * cos(a) / 111_320, s.center.lon + r * sin(a) / (111_320 * cos(Math.toRadians(s.center.lat))))
    }
    if (t >= SquadOverlay.DRIVE) return orbit(t - SquadOverlay.DRIVE)
    val k = (t / SquadOverlay.DRIVE).coerceIn(0.0, 1.0).let { it * it * (3 - 2 * it) } // ease in and out
    val to = orbit(0.0)
    return Spot(station.lat + (to.lat - station.lat) * k, station.lon + (to.lon - station.lon) * k)
}

/** Final screen once a squad confirms the place. The only way out is back to the title. */
@Composable
fun VerdictScreen(onReturnToTitle: () -> Unit) {
    BackHandler {}
    SystemBars(lightBottomIcons = true)
    val accent = Color(0xFFE0B354)
    Column(
        Modifier.fillMaxSize()
            .glow(Color(0xFF0D1117), Glow(Color(0xFF3A2F10), 0.5f, 1f, 1f))
            .safeDrawingPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.case_solved), color = accent, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 4.sp, textAlign = TextAlign.Center)
        Text(
            stringResource(R.string.solved_text),
            Modifier.padding(top = 16.dp), color = Color.White.copy(alpha = 0.82f), fontSize = 17.sp, lineHeight = 24.sp, textAlign = TextAlign.Center,
        )
        Button(
            onReturnToTitle, Modifier.padding(top = 48.dp).height(50.dp), shape = RoundedCornerShape(25.dp),
            contentPadding = PaddingValues(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color(0xFF0D1117)),
        ) { Text(stringResource(R.string.return_title), fontSize = 17.sp, fontWeight = FontWeight.Bold) }
    }
}
