package com.nammamela.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamela.data.models.CastMember

@Dao
interface CastDao {

    @Query("SELECT * FROM cast_members WHERE playId = :playId")
    fun getCastForPlay(playId: Int): LiveData<List<CastMember>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastMember(castMember: CastMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(castMembers: List<CastMember>)

    @Delete
    suspend fun deleteCastMember(castMember: CastMember)

    @Query("DELETE FROM cast_members WHERE playId = :playId")
    suspend fun deleteCastForPlay(playId: Int)
}
