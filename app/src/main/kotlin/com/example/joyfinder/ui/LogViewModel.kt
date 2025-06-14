package com.example.joyfinder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.joyfinder.data.AppDatabase
import com.example.joyfinder.data.LogRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LogViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = LogRepository(AppDatabase.get(application).activityLogDao())

    val logs: StateFlow<List<com.example.joyfinder.data.ActivityLog>> =
        repo.getLogs().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addLog(activity: String, joyScore: Int) {
        viewModelScope.launch {
            repo.addLog(activity, joyScore)
        }
    }
}
