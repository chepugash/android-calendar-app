package com.practice.calendar.data.repository

import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.mapper.EventMapper
import com.practice.calendar.data.remote.EventApi
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class EventRepositoryImpl(
    private val api: EventApi,
    private val dao: EventDao,
    private val eventMapper: EventMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : EventRepository {

    override fun getEventsByDate(date: Long): Flow<List<EventInfo>?> {
        val events = dao.getByDate(date)
        return eventMapper.eventDbEntityListFlowToEventInfoListFlow(events)
    }

    override suspend fun updateEventsFromRemote() = withContext(dispatcher) {
        val remoteEvents = api.getEvents()
        dao.saveEvents(eventMapper.eventResponseEntityListToEventDbEntityList(remoteEvents))
    }

    override fun getEventById(eventId: Long): Flow<EventInfo?> =
        eventMapper.eventDbEntityFlowToEventInfoFlow(dao.getById(eventId))

    override suspend fun createEvent(eventInfo: EventInfo): Long = withContext(dispatcher) {
        dao.createEvent(eventMapper.eventInfoToEventDbEntity(eventInfo))
    }

    override suspend fun deleteEvent(eventId: Long) = withContext(dispatcher) {
        dao.delete(eventId)
    }
}
