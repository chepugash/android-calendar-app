package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase

class UpdateEventsFromRemoteUseCaseImpl(
    private val eventRepository: EventRepository
) : UpdateEventsFromRemoteUseCase {

    override suspend operator fun invoke() {
        eventRepository.updateEventsFromRemote()
    }
}