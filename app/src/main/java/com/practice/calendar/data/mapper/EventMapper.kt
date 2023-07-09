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

class EventMapper {

    private fun eventResponseEntityToEventDbEntity(
        eventResponseEntity: EventResponseEntity
    ): EventDbEntity {
        return EventDbEntity(
            id = eventResponseEntity.id.toLong(),
            dateStart = eventResponseEntity.dateStart.toLong(),
            dateFinish = eventResponseEntity.dateFinish.toLong(),
            name = eventResponseEntity.name,
            description = eventResponseEntity.description
        )
    }

    fun eventResponseEntityListToEventDbEntityList(
        eventResponseList: List<EventResponseEntity>
    ): List<EventDbEntity> {
        return eventResponseList.map {
            eventResponseEntityToEventDbEntity(it)
        }
    }

    private fun eventDbEntityToEventInfo(eventDbEntity: EventDbEntity): EventInfo {
        return EventInfo(
            id = eventDbEntity.id,
            dateStart = timestampToLocalDateTime(eventDbEntity.dateStart),
            dateFinish = timestampToLocalDateTime(eventDbEntity.dateFinish),
            name = eventDbEntity.name,
            description = eventDbEntity.description
        )
    }

    fun eventDbEntityFlowToEventInfoFlow(eventDbEntityFlow: Flow<EventDbEntity?>): Flow<EventInfo?> {
        return eventDbEntityFlow.map {
            if (it != null) {
                eventDbEntityToEventInfo(it)
            } else null
        }
    }

    fun eventDbEntityListFlowToEventInfoListFlow(
        eventDbEntityListFlow: Flow<List<EventDbEntity>?>
    ): Flow<List<EventInfo>?> {
        return eventDbEntityListFlow.map {list ->
            list?.map { entity ->
                eventDbEntityToEventInfo(entity)
            }
        }
    }

    fun eventInfoToEventDbEntity(eventInfo: EventInfo): EventDbEntity {
        return EventDbEntity(
            id = eventInfo.id,
            dateStart = localDateTimeToTimestamp(eventInfo.dateStart),
            dateFinish = localDateTimeToTimestamp(eventInfo.dateFinish),
            name = eventInfo.name,
            description = eventInfo.description
        )
    }

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
}