package com.rukia.police.infrastructure.geocoding

import com.rukia.police.domain.model.Spot
import com.rukia.police.domain.port.PlaceFinder
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/** OpenStreetMap's Nominatim, limited to the map's area (Catalonia). Its policy asks for an identifying user agent. */
class NominatimPlaceFinder(private val userAgent: String) : PlaceFinder {
    override fun find(query: String): Spot? = runCatching {
        // viewbox = left,top,right,bottom
        val url = URL("https://nominatim.openstreetmap.org/search?format=json&limit=1&bounded=1&viewbox=0.1,42.9,3.4,40.45&q=" + URLEncoder.encode(query, "UTF-8"))
        val conn = url.openConnection() as HttpURLConnection
        try {
            conn.setRequestProperty("User-Agent", userAgent)
            conn.connectTimeout = 8_000
            conn.readTimeout = 8_000
            val hit = JSONArray(conn.inputStream.bufferedReader().use { it.readText() }).optJSONObject(0)
            hit?.let { Spot(it.getString("lat").toDouble(), it.getString("lon").toDouble()) }
        } finally {
            conn.disconnect()
        }
    }.getOrNull()
}
