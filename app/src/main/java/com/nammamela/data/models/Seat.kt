package com.nammamela.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "seats",
    foreignKeys = [ForeignKey(
        entity = Play::class,
        parentColumns = ["id"],
        childColumns = ["playId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Seat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,
    val rowLabel: String,    // A, B, C, D, E, F
    val seatNumber: Int,     // 1–10
    val status: SeatStatus = SeatStatus.AVAILABLE,
    val bookedByName: String = "",
    val bookedAt: Long = 0L
) {
    val label: String get() = "$rowLabel$seatNumber"
}

enum class SeatStatus {
    AVAILABLE,
    RESERVED,
    FRONT_ROW   // Special front-row seats
}
