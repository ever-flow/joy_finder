package com.example.joyfinder.util

object OfflineAnalyzer {
    fun analyze(averages: Map<String, Double>): String {
        if (averages.isEmpty()) return "Add some logs first."
        val sorted = averages.entries.sortedByDescending { it.value }
        val lines = sorted.take(3).mapIndexed { index, entry ->
            "${index + 1}. ${entry.key} (avg ${"%.1f".format(entry.value)})"
        }
        return buildString {
            appendLine("Your happiest activities:")
            lines.forEach { appendLine(it) }
            append("Keep enjoying them!")
        }
    }
}
