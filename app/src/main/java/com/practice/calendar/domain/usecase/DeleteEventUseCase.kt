package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.repository.EventRepository

class DeleteEventUseCase(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: Long) {
        eventRepository.deleteEvent(eventId)
    }
}