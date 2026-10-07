package com.rukia.phone

import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

/** Every media file a case names (call audio, chat pictures, profile photos, the police options' images) is in its media folder or the shared one. */
class MediaTest {
    private val path = Regex(""""(?:photo|image)":\s*"([^"]+)"""")
    private val tag = Regex("""#(?:call|image):\s*([^\s#]+)""")

    @Test fun `every media file a case refers to exists`() {
        for (case in File("src/main/assets/cases").listFiles()!!) {
            val json = listOf("characters.json", "police/case.json").map { File(case, it) }.filter { it.exists() }
                .flatMap { f -> path.findAll(f.readText()).map { it.groupValues[1] } }
            val ink = case.walk().filter { it.extension == "ink" }
                .flatMap { f -> f.readLines().filterNot { it.trim().startsWith("//") }.flatMap { l -> tag.findAll(l).map { it.groupValues[1] } } }
            val missing = (json + ink).distinct().filterNot { File(case, "media/$it").exists() || File("src/main/assets/shared/media/$it").exists() }
            assertTrue(missing.isEmpty(), "${case.name}: missing media $missing")
        }
    }
}
