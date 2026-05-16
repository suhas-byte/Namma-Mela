package com.nammamela.data.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nammamela.data.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Play::class, CastMember::class, Seat::class, Applause::class],
    version = 1,
    exportSchema = false
)
abstract class NammaMelaDatabase : RoomDatabase() {

    abstract fun playDao(): PlayDao
    abstract fun castDao(): CastDao
    abstract fun seatDao(): SeatDao
    abstract fun applauseDao(): ApplauseDao

    companion object {
        @Volatile
        private var INSTANCE: NammaMelaDatabase? = null

        fun getDatabase(context: Context): NammaMelaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NammaMelaDatabase::class.java,
                    "namma_mela_db"
                )
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateSampleData(database)
                }
            }
        }

        suspend fun populateSampleData(db: NammaMelaDatabase) {
            // Insert a sample play
            val playId = db.playDao().insertPlay(
                Play(
                    title = "Shakuntala",
                    description = "A timeless classic from Kalidasa, retold with vibrant folk music and traditional costumes of Karnataka.",
                    duration = "2 hrs 45 min",
                    venue = "Rajajinagar Mela Ground, Bengaluru",
                    showDate = "Tonight",
                    showTime = "7:30 PM",
                    posterUrl = "poster_shakuntala",
                    totalRows = 6,
                    seatsPerRow = 10,
                    isActive = true
                )
            ).toInt()

            // Insert cast
            db.castDao().insertAll(listOf(
                CastMember(playId = playId, name = "Ravi Kumar", role = "Lead Actor", photoUrl = "cast_actor", bio = "30 years of classical drama experience."),
                CastMember(playId = playId, name = "Suma Devi", role = "Lead Actress", photoUrl = "cast_actress", bio = "Renowned for her expressive dance-drama."),
                CastMember(playId = playId, name = "Manjunath Rao", role = "Comedian", photoUrl = "cast_comedian", bio = "Crowd favourite for 15 years."),
                CastMember(playId = playId, name = "Kavitha Gowda", role = "Singer", photoUrl = "cast_singer", bio = "Classical Carnatic vocalist.")
            ))

            // Generate seats
            val rows = listOf("A", "B", "C", "D", "E", "F")
            val seats = mutableListOf<Seat>()
            for (row in rows) {
                for (num in 1..10) {
                    val status = if (row == "A") SeatStatus.FRONT_ROW else SeatStatus.AVAILABLE
                    seats.add(Seat(playId = playId, rowLabel = row, seatNumber = num, status = status))
                }
            }
            db.seatDao().insertAll(seats)

            // Sample applause
            db.applauseDao().insertApplause(Applause(playId = playId, fanName = "Anil S.", message = "Simply breathtaking! The best drama I've seen in years. 🙌", emoji = "🌟"))
            db.applauseDao().insertApplause(Applause(playId = playId, fanName = "Priya M.", message = "Kavitha's voice is divine. Came 60km just for this!", emoji = "❤️"))
        }
    }
}
