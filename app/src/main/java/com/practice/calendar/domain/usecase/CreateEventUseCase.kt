package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository

class CreateEventUseCase(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(eventInfo: EventInfo): Long {
        return eventRepository.createEvent(eventInfo)
    }
}