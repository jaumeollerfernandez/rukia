package com.rukia.phone

import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class EffectsTest {
    @Test fun `every effect a story uses exists`() {
        val used = File("src/main/assets/cases").walk().filter { it.extension == "ink" }
            .flatMap { file -> Regex("""#\s*effect:\s*([\w-]+)""").findAll(file.readText()).map { file.path to it.groupValues[1] } }
        for ((path, name) in used) assertTrue(name in effects, "$path uses unknown effect '$name'. Known: ${effects.keys}")
    }
}
