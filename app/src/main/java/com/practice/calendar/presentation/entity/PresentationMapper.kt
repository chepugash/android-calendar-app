package com.practice.calendar.presentation.entity

import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class PresentationMapper {

    fun eventInfoToEventPresentationEntity(eventInfo: EventInfo?): EventPresentationEntity? {
        if (eventInfo == null) return null
        return EventPresentationEntity(
            id = eventInfo.id,
            dateStart = timestampToLocalDateTime(eventInfo.dateStart),
            dateFinish = timestampToLocalDateTime(eventInfo.dateFinish),
            name = eventInfo.name,
            description = eventInfo.description
        )
    }

    fun eventInfoListFlowToEventPresentationEntityListFlow(
        eventInfoListFlow: Flow<List<EventInfo>?>
    ): Flow<List<EventPresentationEntity>?> {
        return eventInfoListFlow.map { list ->
            list?.map { entity ->
                eventInfoToEventPresentationEntity(entity)!!
            }
        }
    }

    private fun timestampToLocalDateTime(timestamp: Long): LocalDateTime {
        val instant = Instant.ofEpochMilli(timestamp)
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    fun localDateTimeToTimestamp(ldt: LocalDateTime): Long =
        ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

    fun localDateToTimestamp(ld: LocalDate): Long =
        ld.atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
