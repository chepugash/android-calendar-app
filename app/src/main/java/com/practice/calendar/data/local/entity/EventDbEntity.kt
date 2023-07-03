package com.practice.calendar.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "events",
    indices = [
        Index("date_start")
    ]
)
data class EventDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_finish") val dateFinish: Long,
    val name: String,
    val description: String
)