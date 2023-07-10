package com.practice.calendar.domain.repository

import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getEventsByDate(date: Long): Flow<List<EventInfo>?>

    suspend fun updateEventsFromRemote()

    fun getEventById(eventId: Long): Flow<EventInfo?>

    suspend fun createEvent(eventInfo: EventInfo): Long

    suspend fun deleteEvent(eventId: Long)
}
