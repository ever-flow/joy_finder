package com.example.joyfinder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.joyfinder.data.AppDatabase
import com.example.joyfinder.data.LogRepository
import com.example.joyfinder.util.OfflineAnalyzer
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = LogRepository(AppDatabase.get(application).activityLogDao())

    val logs: StateFlow<List<com.example.joyfinder.data.ActivityLog>> =
        repo.getLogs().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val activityAverages: StateFlow<Map<String, Double>> =
        repo.getLogs().map { entries ->
            entries.groupBy { it.activity }
                .mapValues { (_, list) ->
                    list.map { it.joyScore }.average()
                }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyMap())

    fun addLog(activity: String, joyScore: Int) {
        viewModelScope.launch {
            repo.addLog(activity, joyScore)
        }
    }
    fun generatePrompt(averages: Map<String, Double>): String {
        if (averages.isEmpty()) return "No logs yet.";
        val summary = averages.entries.joinToString("\n") { (act, avg) ->
            "- $act: ${"%.1f".format(avg)}"
        }
        return "Here are my recent activities and average joy scores:\n$summary\nPlease analyze my patterns and give suggestions."
    }
    fun offlineAnalysis(averages: Map<String, Double>): String {
        return OfflineAnalyzer.analyze(averages)
    }

}
