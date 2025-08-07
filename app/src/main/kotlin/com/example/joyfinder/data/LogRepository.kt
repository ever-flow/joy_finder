package com.example.joyfinder.data

import kotlinx.coroutines.flow.Flow

class LogRepository(private val dao: ActivityLogDao) {
    fun getLogs(): Flow<List<ActivityLog>> = dao.getLogs()
    suspend fun addLog(activity: String, joyScore: Int) {
        dao.insert(ActivityLog(timestamp = System.currentTimeMillis(), activity = activity, joyScore = joyScore))
    }
}
