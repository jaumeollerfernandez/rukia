package com.rukia.chat.infrastructure.persistence

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File

/**
 * Reads a case's story the way a writer needs it: every .ink line with its file and number, which variables each line
 * sets or reads, and the case time it falls on. [StoryIndexTest] turns it into the case's INDICE.md and
 * [StoryRulesTest] checks the story's rules with it. Plain text parsing, no ink runtime: it sees what's written.
 */
class StoryIndex(val case: File) {
    class Line(val file: String, val number: Int, val text: String) {
        val trimmed = text.trim()
        val isComment = trimmed.startsWith("//")
        val where get() = "$file:$number"

        /** The text a player would read: no tags, no choice brackets, no ink logic. */
        val spoken get() = trimmed.substringBefore(" #").removePrefix("~").trim()
    }

    class Variable(val name: String, val comment: String, val sets: List<Line>, val reads: List<Line>, val byApp: String?)

    class Timed(val day: Int, val time: String, val speaker: String, val line: Line)

    val lines: List<Line> = File(case, "story").listFiles { f -> f.extension == "ink" }!!.sortedBy { it.name }.flatMap { f ->
        f.readLines().mapIndexed { i, text -> Line(f.name, i + 1, text) }
    }
    private val json = Json { ignoreUnknownKeys = true }
    val records: JsonArray = File(case, "police/records.json").takeIf { it.exists() }
        ?.let { json.parseToJsonElement(it.readText()).jsonObject["records"]!!.jsonArray } ?: JsonArray(emptyList())
    val characters: JsonArray = json.parseToJsonElement(File(case, "characters.json").readText()).jsonArray

    private val varLine = Regex("""^VAR\s+(\w+)\s*=\s*[^/]*(?://\s*(.*))?$""")
    private val setRegex = Regex("""^~\s*(?:temp\s+)?(\w+)\s*(=|\+=|-=)""")

    val variables: List<Variable> by lazy {
        val declared = lines.filter { it.file == "main.ink" }.mapNotNull { l -> varLine.find(l.trimmed)?.let { it.groupValues[1] to it.groupValues[2].trim() } }
        declared.map { (name, comment) ->
            val word = Regex("""\b${Regex.escape(name)}\b""")
            val sets = lines.filter { !it.isComment && setRegex.find(it.trimmed)?.groupValues?.get(1) == name }
            val reads = lines.filter { l -> !l.isComment && !l.trimmed.startsWith("VAR ") && l !in sets && word.containsMatchIn(l.trimmed.substringBefore(" //")) }
            Variable(name, comment, sets, reads, appSets(name))
        }
    }

    /** Variables the app sets from outside the story (see the README, «Story variables the phone sets»). */
    private fun appSets(name: String): String? = when {
        name == "dia" || name == "hora" -> "el reloj del caso, antes de cada paso"
        name == "caso_resuelto" -> "Policía, al acertar la zona en el mapa"
        name == "ultimo_intento" -> "Policía, al mandar una búsqueda"
        name == "final_caso" -> null
        Regex("""llamada_\w+_contestada""").matches(name) -> "el teléfono, al atender o rechazar una llamada de ese chat"
        name.startsWith("ficha_") && records.any { it.jsonObject["id"]?.jsonPrimitive?.content == name.removePrefix("ficha_") } ->
            "Policía, al leer la ficha «${name.removePrefix("ficha_")}»"
        else -> null
    }

    /** Record ids whose `unlockedBy` names this story variable. */
    fun recordsUnlockedBy(name: String) = records.filter { r ->
        (r.jsonObject["unlockedBy"] as? JsonArray)?.any { it.jsonPrimitive.content == name } == true
    }.map { it.jsonObject["id"]!!.jsonPrimitive.content }

    private val atRegex = Regex("""#at:\s*D(-?\d+)\s+(\d{1,2}:\d{2})""")
    private val fromRegex = Regex("""#from:\s*(\w+)""")

    /** Every line with its own case time (`#at`), in order. */
    val timeline: List<Timed> by lazy {
        lines.filter { !it.isComment }.mapNotNull { l ->
            atRegex.find(l.text)?.let { m ->
                val speaker = fromRegex.find(l.text)?.groupValues?.get(1) ?: l.file.removeSuffix(".ink")
                Timed(m.groupValues[1].toInt(), m.groupValues[2].padStart(5, '0'), speaker, l)
            }
        }.sortedWith(compareBy({ it.day }, { it.time }, { it.line.file }, { it.line.number }))
    }

    /** The case day of the nearest `#at` above [line] in its file, or null if there's none (a chat the player opens). */
    fun dayOf(line: Line): Int? = lines.subList(0, lines.indexOf(line) + 1).asReversed()
        .takeWhile { it.file == line.file }.firstNotNullOfOrNull { l -> atRegex.find(l.text)?.groupValues?.get(1)?.toInt() }

    /** Contacts and hidden chats, with the words that name them in the text. */
    val people: List<Pair<String, List<String>>> by lazy {
        characters.map { it.jsonObject }.filter { it["id"]!!.jsonPrimitive.content != "me" }.map { c ->
            val id = c["id"]!!.jsonPrimitive.content
            val shown = c["name"]!!.jsonPrimitive.content.substringBefore(" (").replace(Regex("""[^\p{L} ]"""), "").trim()
            val names = (listOf(shown.substringBefore(" ")) + (extraNames[id] ?: emptyList())).filter { it.length > 2 }.distinct()
            "$id (${c["name"]!!.jsonPrimitive.content})" to names
        }
    }

    // How the story names those whose chat shows something else (a number, «Mamá», «Papá»...).
    private val extraNames = mapOf(
        "mama" to listOf("Montse"), "jordi" to listOf("Jordi"), "guia" to listOf("Ignasi"), "iker" to listOf("Iker"),
        "rosalia" to listOf("Rosalia"), "desconocido" to listOf("prepago"), "laia" to listOf("Laia"),
    )

    fun mentions(names: List<String>): List<Line> {
        val words = names.map { Regex("""(?<![\p{L}])${Regex.escape(it)}(?![\p{L}])""", RegexOption.IGNORE_CASE) }
        return lines.filter { l -> !l.isComment && !l.trimmed.startsWith("VAR ") && !l.trimmed.startsWith("INCLUDE ") && words.any { it.containsMatchIn(l.spoken) } }
    }

    /** INDICE.md: variables, timeline and who's mentioned where. Generated: never edited by hand. */
    fun markdown(): String = buildString {
        appendLine("# Índice de la historia (generado)")
        appendLine()
        appendLine("Lo genera `StoryIndexTest` a partir de los `.ink`, `characters.json` y `police/records.json`. **No lo edites a mano**: si cambias la historia, pasa los tests y se regenera.")
        appendLine("Para buscar algo, empieza aquí y ve a las líneas `archivo:línea` que indica.")
        appendLine()
        appendLine("## Variables")
        appendLine()
        appendLine("| Variable | Qué es | La pone | La lee |")
        appendLine("|---|---|---|---|")
        for (v in variables) {
            val setters = listOfNotNull(v.byApp?.let { "*(app: $it)*" }) + v.sets.map { "`${it.where}`" }
            val readers = v.reads.map { "`${it.where}`" } + recordsUnlockedBy(v.name).map { "*(desbloquea la ficha «$it»)*" }
            appendLine("| `${v.name}` | ${v.comment.replace("|", "/")} | ${setters.joinToString(" ").ifEmpty { "—" }} | ${cap(readers)} |")
        }
        appendLine()
        appendLine("## Cronología")
        appendLine()
        appendLine("Cada línea con hora propia (`#at`). Las respuestas con `#delay` llegan después de la suya y no salen aquí.")
        for ((day, items) in timeline.groupBy { it.day }) {
            appendLine()
            appendLine(if (day < 1) "### D$day (historial del móvil)" else "### D$day")
            appendLine()
            for (t in items) appendLine("- ${t.time} · **${t.speaker}** · ${clip(t.line.spoken, 110).replace("|", "/")} · `${t.line.where}`")
        }
        appendLine()
        appendLine("## Personajes")
        appendLine()
        appendLine("Dónde se les nombra (texto que lee el jugador, sin comentarios). «Desde» es el día del `#at` más cercano por encima; «charla» si está en una charla sin hora.")
        appendLine()
        appendLine("| Personaje | Se le busca por | Desde | Archivos |")
        appendLine("|---|---|---|---|")
        for ((who, names) in people) {
            val found = mentions(names)
            val first = found.mapNotNull { dayOf(it) }.minOrNull()?.let { "D$it" } ?: if (found.isEmpty()) "—" else "charla"
            val files = found.groupBy { it.file }.map { (f, ls) -> "$f (${ls.size})" }
            appendLine("| $who | ${names.joinToString(", ")} | $first | ${files.joinToString(", ").ifEmpty { "—" }} |")
        }
    }

    /** The first [max] characters, without splitting an emoji in two. */
    private fun clip(text: String, max: Int) = text.take(max).let { if (it.lastOrNull()?.isHighSurrogate() == true) it.dropLast(1) else it }

    private fun cap(items: List<String>, max: Int = 14) =
        if (items.isEmpty()) "—" else items.take(max).joinToString(" ") + if (items.size > max) " … (+${items.size - max})" else ""
}
