package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetEventsUseCase(
    private val eventRepository: EventRepository
) {

    operator fun invoke(date: LocalDate): Flow<List<EventInfo>?> {
        return eventRepository.getEventsByDate(date)
    }
}