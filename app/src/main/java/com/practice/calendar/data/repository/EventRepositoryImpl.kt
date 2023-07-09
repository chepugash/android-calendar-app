package com.practice.calendar.data.repository

import android.util.Log
import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.mapper.EventMapper
import com.practice.calendar.data.remote.EventApi
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class EventRepositoryImpl(
    private val api: EventApi,
    private val dao: EventDao,
    private val eventMapper: EventMapper
) : EventRepository {

    override fun getEventsByDate(date: LocalDate): Flow<List<EventInfo>?> {
        val events = dao.getByDate(eventMapper.localDateToTimestamp(date))
        return eventMapper.eventDbEntityListFlowToEventInfoListFlow(events)
    }

    override suspend fun updateEventsFromRemote() {
        val remoteEvents = api.getEvents()
        dao.saveEvents(eventMapper.eventResponseEntityListToEventDbEntityList(remoteEvents))
    }

    override fun getEventById(eventId: Long): Flow<EventInfo?> {
        return eventMapper.eventDbEntityFlowToEventInfoFlow(dao.getById(eventId))
    }

    override suspend fun createEvent(eventInfo: EventInfo): Long {
        return dao.createEvent(eventMapper.eventInfoToEventDbEntity(eventInfo))
    }

    override suspend fun deleteEvent(eventId: Long) {
        dao.delete(eventId)
    }
}