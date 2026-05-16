package com.nammamela.data.repository

import android.content.Context
import com.nammamela.data.models.*

class NammaMelaRepository(context: Context) {

    private val db = NammaMelaDatabase.getDatabase(context)
    private val playDao = db.playDao()
    private val castDao = db.castDao()
    private val seatDao = db.seatDao()
    private val applauseDao = db.applauseDao()

    // --- Plays ---
    val allPlays = playDao.getAllPlays()
    val tonightsPlay = playDao.getTonightsPlay()

    suspend fun insertPlay(play: Play): Long = playDao.insertPlay(play)
    suspend fun updatePlay(play: Play) = playDao.updatePlay(play)
    suspend fun deletePlay(play: Play) = playDao.deletePlay(play)
    suspend fun setActivePlay(id: Int) {
        playDao.clearActivePlay()
        playDao.setActivePlay(id)
    }

    // --- Cast ---
    fun getCastForPlay(playId: Int) = castDao.getCastForPlay(playId)
    suspend fun insertCastMember(castMember: CastMember) = castDao.insertCastMember(castMember)
    suspend fun deleteCastMember(castMember: CastMember) = castDao.deleteCastMember(castMember)
    suspend fun replaceCast(playId: Int, members: List<CastMember>) {
        castDao.deleteCastForPlay(playId)
        castDao.insertAll(members)
    }

    // --- Seats ---
    fun getSeatsForPlay(playId: Int) = seatDao.getSeatsForPlay(playId)
    fun getAvailableCount(playId: Int) = seatDao.getAvailableCount(playId)

    suspend fun reserveSeat(seat: Seat, bookedBy: String) {
        seatDao.updateSeatStatus(
            seatId = seat.id,
            status = SeatStatus.RESERVED,
            name = bookedBy,
            time = System.currentTimeMillis()
        )
    }

    suspend fun initSeatsForPlay(play: Play) {
        val existing = seatDao.getSeatsForPlaySync(play.id)
        if (existing.isEmpty()) {
            val rows = listOf("A", "B", "C", "D", "E", "F")
            val seats = mutableListOf<Seat>()
            for (row in rows) {
                for (num in 1..play.seatsPerRow) {
                    val status = if (row == "A") SeatStatus.FRONT_ROW else SeatStatus.AVAILABLE
                    seats.add(Seat(playId = play.id, rowLabel = row, seatNumber = num, status = status))
                }
            }
            seatDao.insertAll(seats)
        }
    }

    // --- Applause ---
    fun getApplauseForPlay(playId: Int) = applauseDao.getApplauseForPlay(playId)
    suspend fun postApplause(applause: Applause) = applauseDao.insertApplause(applause)
}
