package com.practice.calendar.data.local.entity

import androidx.room.ColumnInfo

data class EventItemTuple(
    val id: Long,
    @ColumnInfo(name = "date_start") val dateStart: Long,
    @ColumnInfo(name = "date_finish") val dateFinish: Long,
    val name: String
)