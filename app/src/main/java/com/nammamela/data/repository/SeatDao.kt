package com.nammamela.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nammamela.data.models.Seat
import com.nammamela.data.models.SeatStatus

@Dao
interface SeatDao {

    @Query("SELECT * FROM seats WHERE playId = :playId ORDER BY rowLabel, seatNumber")
    fun getSeatsForPlay(playId: Int): LiveData<List<Seat>>

    @Query("SELECT * FROM seats WHERE playId = :playId ORDER BY rowLabel, seatNumber")
    suspend fun getSeatsForPlaySync(playId: Int): List<Seat>

    @Query("SELECT COUNT(*) FROM seats WHERE playId = :playId AND status = 'AVAILABLE'")
    fun getAvailableCount(playId: Int): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(seats: List<Seat>)

    @Update
    suspend fun updateSeat(seat: Seat)

    @Query("UPDATE seats SET status = :status, bookedByName = :name, bookedAt = :time WHERE id = :seatId")
    suspend fun updateSeatStatus(seatId: Int, status: SeatStatus, name: String, time: Long)

    @Query("DELETE FROM seats WHERE playId = :playId")
    suspend fun deleteSeatsForPlay(playId: Int)
}
