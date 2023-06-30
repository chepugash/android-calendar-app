package com.practice.calendar.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.practice.calendar.data.local.entity.EventDbEntity
import com.practice.calendar.data.local.entity.EventItemTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE id = :id")
    fun getById(id: Long): Flow<EventDbEntity?>

    @Query("SELECT id, date_start, date_finish, name FROM events " +
            "WHERE date_start >= :dateStart AND date_finish < :dateFinish")
    fun getByDate(
        dateStart: Long,
        dateFinish: Long = dateStart + SECONDS_IN_DAY
    ): Flow<List<EventItemTuple>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createEvent(eventDbEntity: EventDbEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEvents(events: List<EventDbEntity>)

    companion object {
        private const val SECONDS_IN_DAY = 86400
    }
}