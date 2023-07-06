package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.repository.EventRepository

class UpdateEventsFromRemoteUseCase(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke() {
        eventRepository.updateEventsFromRemote()
    }
}