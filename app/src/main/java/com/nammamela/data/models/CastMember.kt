package com.nammamela.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cast_members",
    foreignKeys = [ForeignKey(
        entity = Play::class,
        parentColumns = ["id"],
        childColumns = ["playId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class CastMember(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val playId: Int,
    val name: String,
    val role: String,       // Actor, Comedian, Singer, Director
    val photoUrl: String,
    val bio: String = ""
)
