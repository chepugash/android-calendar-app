package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.CreateEventUseCase

class CreateEventUseCaseImpl(
    private val eventRepository: EventRepository
) : CreateEventUseCase {

    override suspend operator fun invoke(eventInfo: EventInfo): Long =
        eventRepository.createEvent(eventInfo)
}