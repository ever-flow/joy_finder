package com.example.joyfinder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ActivityLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val activity: String,
    val joyScore: Int
)
