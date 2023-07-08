package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow

class GetEventUseCase(
    private val eventRepository: EventRepository
) {

    operator fun invoke(eventId: Long): Flow<EventInfo?> {
        return eventRepository.getEventById(eventId)
    }
}