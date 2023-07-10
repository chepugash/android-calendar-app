package com.practice.calendar.util

import com.practice.calendar.presentation.entity.EventPresentationEntity
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val TIME_FORMAT = "HH:mm"
const val DATE_FORMAT = "d MMM, yyyy"
const val MINUTES_IN_HOUR = 60

fun LocalDateTime.formatToTime(): String {
    return this.format(DateTimeFormatter.ofPattern(TIME_FORMAT))
}

fun LocalDateTime.formatToDate(): String {
    return this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
}

fun LocalDate.formatToDate(): String {
    return this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
}

fun LocalTime.formatToTime(): String {
    return this.format(DateTimeFormatter.ofPattern(TIME_FORMAT))
}

fun LocalDateTime.timeInMinutes(): Int {
    return this.hour * MINUTES_IN_HOUR + this.minute
}

fun Flow<List<EventPresentationEntity>?>.groupByTime(): Flow<EventsGroupedByTime> {
    return this.map {list ->
        if (list != null) {
            val intersectingEvents = mutableListOf<List<EventPresentationEntity>>()
            val visited = BooleanArray(list.size) { false }

            for (i in list.indices) {
                if (!visited[i]) {
                    val intersectingEvent = mutableListOf<EventPresentationEntity>()
                    intersectingEvent.add(list[i])
                    visited[i] = true

                    for (j in i + 1 until list.size) {
                        if (!visited[j] && isIntersecting(list[i], list[j])) {
                            intersectingEvent.add(list[j])
                            visited[j] = true
                        }
                    }

                    intersectingEvents.add(intersectingEvent.toPersistentList())
                }
            }

            return@map intersectingEvents.toPersistentList()
        } else {
            return@map null
        }
    }
}

private fun isIntersecting(event1: EventPresentationEntity, event2: EventPresentationEntity): Boolean {
    return event1.dateStart <= event2.dateFinish && event2.dateStart <= event1.dateFinish
}
