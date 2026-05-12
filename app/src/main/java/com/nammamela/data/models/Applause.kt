package com.nammamela.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "applause",
    foreignKeys = [ForeignKey(
        entity = Play::class,
        parentColumns = ["id"],
        childColumns = ["playId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Applause(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,
    val fanName: String,
    val message: String,
    val emoji: String = "👏",
    val postedAt: Long = System.currentTimeMillis()
)
