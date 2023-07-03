package com.practice.calendar.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.local.entity.EventDbEntity

@Database(version = 1, entities = [EventDbEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getEventDao(): EventDao

    companion object {
        const val DB_NAME = "AppDatabase"
    }
}