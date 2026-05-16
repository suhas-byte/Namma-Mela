package com.nammamela.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamela.data.models.Applause

@Dao
interface ApplauseDao {

    @Query("SELECT * FROM applause WHERE playId = :playId ORDER BY postedAt DESC")
    fun getApplauseForPlay(playId: Int): LiveData<List<Applause>>

    @Insert
    suspend fun insertApplause(applause: Applause)

    @Delete
    suspend fun deleteApplause(applause: Applause)
}
