package com.practice.calendar.data.mapper

import com.practice.calendar.data.local.entity.EventDbEntity
import com.practice.calendar.data.remote.response.EventResponseEntity
import com.practice.calendar.domain.entity.EventInfo
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

// remote entity to local
fun EventResponseEntity.toEventDbEntity(): EventDbEntity = EventDbEntity(
    id = id.toLong(),
    dateStart = dateStart.toLong(),
    dateFinish = dateFinish.toLong(),
    name = name,
    description = description
)

// data layer entity to domain layer entity
fun EventDbEntity.toEventInfo(): EventInfo = EventInfo(
    id = id,
    dateStart = timestampToLocalDateTime(dateStart),
    dateFinish = timestampToLocalDateTime(dateFinish),
    name = name,
    description = description
)

// domain layer entity to data layer
fun EventInfo.toEventDbEntity(): EventDbEntity = EventDbEntity(
    id = id,
    dateStart = localDateTimeToTimestamp(dateStart),
    dateFinish = localDateTimeToTimestamp(dateFinish),
    name = name,
    description = description
)

private fun timestampToLocalDateTime(timestamp: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(timestamp)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

private fun localDateTimeToTimestamp(ldt: LocalDateTime): Long {
    return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}