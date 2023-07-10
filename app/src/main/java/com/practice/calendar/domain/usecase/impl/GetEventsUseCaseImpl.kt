package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.Flow

class GetEventsUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventsUseCase {

    override operator fun invoke(date: Long): Flow<List<EventInfo>?> =
        eventRepository.getEventsByDate(date)
}
