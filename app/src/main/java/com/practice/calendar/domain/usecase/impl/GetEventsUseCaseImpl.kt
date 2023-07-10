package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.util.EventsGroupedByTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetEventsUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventsUseCase {

    override operator fun invoke(date: LocalDate): Flow<List<EventInfo>?> =
        eventRepository.getEventsByDate(date)
}