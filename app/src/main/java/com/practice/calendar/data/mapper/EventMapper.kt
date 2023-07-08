package com.practice.calendar.data.mapper

import com.practice.calendar.data.local.entity.EventDbEntity
import com.practice.calendar.data.remote.response.EventResponseEntity
import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

// remote entity to local
fun EventResponseEntity.toEventDbEntity(): EventDbEntity = EventDbEntity(
    id = id.toLong(),
    dateStart = dateStart.toLong(),
    dateFinish = dateFinish.toLong(),
    name = name,
    description = description
)

// remote list of entities to local
fun List<EventResponseEntity>.toEventDbEntityList(): List<EventDbEntity> = map {
    it.toEventDbEntity()
}

// data layer entity to domain layer entity
fun EventDbEntity.toEventInfo(): EventInfo = EventInfo(
    id = id,
    dateStart = timestampToLocalDateTime(dateStart),
    dateFinish = timestampToLocalDateTime(dateFinish),
    name = name,
    description = description
)

fun Flow<EventDbEntity?>.toEventInfoFlow(): Flow<EventInfo?> = map {
    it?.toEventInfo()
}

fun Flow<List<EventDbEntity>?>.toEventInfoListFlow(): Flow<List<EventInfo>?> {
    return this.map {list ->
        list?.map { entity -> entity.toEventInfo() }
    }
}

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

fun localDateToTimestamp(ld: LocalDate): Long {
    return ld.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}