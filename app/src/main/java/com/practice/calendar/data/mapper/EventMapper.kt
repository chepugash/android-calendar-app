package com.practice.calendar.data.mapper

import com.practice.calendar.data.local.entity.EventDbEntity
import com.practice.calendar.data.remote.response.EventResponseEntity
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.util.localDateTimeToTimestamp
import com.practice.calendar.util.timestampToLocalDateTime

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

// domain layer entity to data layer
fun EventInfo.toEventDbEntity(): EventDbEntity = EventDbEntity(
    id = id,
    dateStart = localDateTimeToTimestamp(dateStart),
    dateFinish = localDateTimeToTimestamp(dateFinish),
    name = name,
    description = description
)
