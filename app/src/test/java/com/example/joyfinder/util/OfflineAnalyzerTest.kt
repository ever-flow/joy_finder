package com.example.joyfinder.util

import kotlin.test.Test
import kotlin.test.assertEquals

class OfflineAnalyzerTest {
    @Test
    fun returnsTopActivitiesInOrder() {
        val averages = mapOf(
            "A" to 2.0,
            "B" to 5.0,
            "C" to 3.0,
            "D" to 1.0,
        )

        val result = OfflineAnalyzer.analyze(averages)
        val lines = result.lines().filter { it.matches(Regex("\\d+\\..*")) }

        assertEquals("1. B (avg 5.0)", lines[0])
        assertEquals("2. C (avg 3.0)", lines[1])
        assertEquals("3. A (avg 2.0)", lines[2])
    }
}

