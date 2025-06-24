package com.example.joyfinder.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityLogDao {
    @Query("SELECT * FROM ActivityLog ORDER BY timestamp DESC")
    fun getLogs(): Flow<List<ActivityLog>>

    @Insert
    suspend fun insert(log: ActivityLog)
}
