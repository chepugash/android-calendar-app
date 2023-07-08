package com.practice.calendar.domain.repository

import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface EventRepository {

    fun getEventsByDate(date: LocalDate): Flow<List<EventInfo>?>

    suspend fun updateEventsFromRemote()

    fun getEventById(eventId: Long): Flow<EventInfo?>

    suspend fun createEvent(eventInfo: EventInfo): Long
}