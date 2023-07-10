package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.GetEventUseCase
import kotlinx.coroutines.flow.Flow

class GetEventUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventUseCase {

    override operator fun invoke(eventId: Long): Flow<EventInfo?> =
        eventRepository.getEventById(eventId)
}
