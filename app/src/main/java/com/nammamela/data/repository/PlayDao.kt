package com.nammamela.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamela.data.models.Play

@Dao
interface PlayDao {

    @Query("SELECT * FROM plays ORDER BY id DESC")
    fun getAllPlays(): LiveData<List<Play>>

    @Query("SELECT * FROM plays WHERE isActive = 1 LIMIT 1")
    fun getTonightsPlay(): LiveData<Play?>

    @Query("SELECT * FROM plays WHERE id = :id")
    suspend fun getPlayById(id: Int): Play?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlay(play: Play): Long

    @Update
    suspend fun updatePlay(play: Play)

    @Delete
    suspend fun deletePlay(play: Play)

    @Query("UPDATE plays SET isActive = 0")
    suspend fun clearActivePlay()

    @Query("UPDATE plays SET isActive = 1 WHERE id = :id")
    suspend fun setActivePlay(id: Int)
}
