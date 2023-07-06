package com.practice.calendar.data.repository

import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.mapper.localDateToTimestamp
import com.practice.calendar.data.mapper.toEventDbEntityList
import com.practice.calendar.data.mapper.toEventInfoFlow
import com.practice.calendar.data.remote.EventApi
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventRepositoryImpl(
    private val api: EventApi,
    private val dao: EventDao
) : EventRepository {

    override fun getEventsByDate(date: LocalDate): Flow<List<EventInfo>?> {
        return dao.getByDate(localDateToTimestamp(date)).toEventInfoFlow()
    }

    override suspend fun updateEventsFromRemote() {
        val remoteEvents = api.getEvents()
        dao.saveEvents(remoteEvents.toEventDbEntityList())
    }
}