package com.nammamela.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plays")
data class Play(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val duration: String,        // e.g. "2 hrs 30 min"
    val venue: String,
    val showDate: String,        // e.g. "15 May 2026"
    val showTime: String,        // e.g. "7:00 PM"
    val posterUrl: String,       // URL or local resource name
    val totalRows: Int = 6,
    val seatsPerRow: Int = 10,
    val isActive: Boolean = true // Tonight's play flag
)
