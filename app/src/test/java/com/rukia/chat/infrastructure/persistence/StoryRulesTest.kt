package com.rukia.chat.infrastructure.persistence

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Rules of the RUK-93429049 plot that are easy to break when editing: who can be named when, plates that must match,
 * choices that must not hang. Seconds, not the whole week (that's CaseWeekTest). Add a rule here when a mistake slips in.
 */
class StoryRulesTest {
    private val index = StoryIndex(File("src/main/assets/cases/ruk-93429049"))

    /**
     * The main suspect's name only comes out by investigating the association: a line naming him must be behind one of
     * [gates] (in itself, or since the start of its choice, block or stitch), or right after setting one of them.
     */
    private class Secret(val name: String, val gates: List<String>)

    private val secrets = listOf(Secret("Ignasi", listOf("sabe_ignasi", "ficha_ignasi", "final_")))

    @Test fun `secret names only show up once the player dug them out`() {
        val blockStart = Regex("""^(\*|\+|=|\{|-\s)""")
        for (secret in secrets) {
            val word = Regex("""(?<![\p{L}])${secret.name}(?![\p{L}])""", RegexOption.IGNORE_CASE)
            val leaks = index.lines.filter { l -> !l.isComment && !l.trimmed.startsWith("VAR ") && word.containsMatchIn(l.trimmed) }.filter { l ->
                val inFile = index.lines.filter { it.file == l.file }
                val above = inFile.subList(0, l.number).asReversed() // the line itself first
                val block = above.takeWhile { !blockStart.containsMatchIn(it.trimmed) } + listOfNotNull(above.firstOrNull { blockStart.containsMatchIn(it.trimmed) })
                block.none { b -> secret.gates.any { it in b.text } }
            }
            assertTrue(leaks.isEmpty(), "«${secret.name}» sale sin investigar en:\n" + leaks.joinToString("\n") { "${it.where}  ${it.trimmed.take(90)}" })

            val case = index.case
            for (file in listOf("characters.json", "gonpi/gonpi.json") + (File(case, "chats").list()?.map { "chats/$it" } ?: emptyList())) {
                assertTrue(!word.containsMatchIn(File(case, file).readText()), "«${secret.name}» sale en $file, que se ve desde el principio")
            }
            for (r in index.records) {
                val o = r.jsonObject
                val locked = (o["unlockedBy"] as? JsonArray)?.isNotEmpty() == true
                assertTrue(locked || !word.containsMatchIn(o.toString()), "La ficha «${o["id"]!!.jsonPrimitive.content}» nombra a ${secret.name} y se ve desde el principio: ponle unlockedBy")
            }
        }
    }

    @Test fun `a vehicle's plate is the one the story gives`() {
        val plate = Regex("""([A-Z0-9]{3,4})\s*$""")
        for (r in index.records.map { it.jsonObject }.filter { it["vehicle"]?.jsonPrimitive?.content == "true" }) {
            val id = r["id"]!!.jsonPrimitive.content
            val end = plate.find(r["reference"]!!.jsonPrimitive.content)?.groupValues?.get(1) ?: continue
            val storyVars = (r["unlockedBy"] as? JsonArray)?.map { it.jsonPrimitive.content }?.filter { !it.startsWith("ficha:") } ?: continue
            val setters = index.variables.filter { it.name in storyVars }.flatMap { it.sets }.map { it.file }.toSet()
            assertTrue(setters.isNotEmpty(), "Nada en la historia desbloquea la ficha «$id» (${storyVars.joinToString()})")
            assertTrue(setters.any { f -> index.lines.any { it.file == f && end in it.text } }, "La matrícula de «$id» (…$end) no sale en ningún chat que la desbloquee: $setters")
        }
    }

    @Test fun `records only unlock with variables and records that exist`() {
        val vars = index.variables.map { it.name }.toSet()
        val ids = index.records.map { it.jsonObject["id"]!!.jsonPrimitive.content }.toSet()
        for (r in index.records.map { it.jsonObject }) for (c in (r["unlockedBy"] as? JsonArray).orEmpty().map { it.jsonPrimitive.content }) {
            val ok = if (c.startsWith("ficha:")) c.removePrefix("ficha:") in ids else c in vars
            assertTrue(ok, "La ficha «${r["id"]!!.jsonPrimitive.content}» se desbloquea con «$c», que no existe")
        }
    }

    @Test fun `map places only unlock with variables that exist`() {
        val vars = index.variables.map { it.name }.toSet()
        val case = kotlinx.serialization.json.Json.parseToJsonElement(File(index.case, "police/case.json").readText()).jsonObject
        for (p in (case["places"] as? JsonArray).orEmpty().map { it.jsonObject }) for (v in (p["unlockedBy"] as? JsonArray).orEmpty().map { it.jsonPrimitive.content }) {
            assertTrue(v in vars, "El lugar «${p["name"]!!.jsonPrimitive.content}» del mapa se desbloquea con «$v», que no existe")
        }
    }

    /**
     * A choice the player can leave unanswered must expire, or the chat waits for ever (and the story behind it).
     * Top-level choices already get one (`#caduca`); nested ones (`**`) need their own and a hidden `(sin responder)`.
     */
    @Test fun `nested choices expire and can be left unanswered`() {
        val choice = Regex("""^(\*+|\++)\s""")
        val gather = Regex("""^(-\s*)+(?!>)""")
        val problems = mutableListOf<String>()
        for ((file, lines) in index.lines.groupBy { it.file }) {
            for ((i, l) in lines.withIndex()) {
                val depth = choice.find(l.trimmed)?.groupValues?.get(1)?.length ?: continue
                if (depth < 2) continue
                // The first choice of its group: the line above that's a choice is the parent (shallower).
                val parent = (i - 1 downTo 0).firstOrNull { j -> choice.containsMatchIn(lines[j].trimmed) || gather.containsMatchIn(lines[j].trimmed) } ?: continue
                val parentDepth = choice.find(lines[parent].trimmed)?.groupValues?.get(1)?.length ?: continue
                if (parentDepth >= depth) continue
                val siblings = lines.drop(i).takeWhile { s ->
                    val d = choice.find(s.trimmed)?.groupValues?.get(1)?.length
                    val g = gather.find(s.trimmed)?.value?.count { it == '-' }
                    (d == null || d >= depth) && (g == null || g >= depth)
                }.filter { choice.find(it.trimmed)?.groupValues?.get(1)?.length == depth }
                val expires = lines.subList(parent + 1, i).any { "#caduca" in it.text }
                val silent = siblings.any { "(sin responder)" in it.text }
                if (!expires || !silent) problems += "${l.where}  ${if (!expires) "sin #caduca " else ""}${if (!silent) "sin (sin responder)" else ""}"
            }
        }
        assertTrue(problems.isEmpty(), "Elecciones anidadas que pueden quedarse colgadas:\n" + problems.joinToString("\n"))
    }
}
