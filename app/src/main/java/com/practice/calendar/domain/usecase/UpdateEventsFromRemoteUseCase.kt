package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.repository.EventRepository
import javax.inject.Inject

class UpdateEventsFromRemoteUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke() {
        eventRepository.updateEventsFromRemote()
    }
}