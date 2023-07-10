package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.DeleteEventUseCase

class DeleteEventUseCaseImpl(
    private val eventRepository: EventRepository
) : DeleteEventUseCase {

    override suspend operator fun invoke(eventId: Long) =
        eventRepository.deleteEvent(eventId)
}
