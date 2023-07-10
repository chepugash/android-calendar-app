package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo

interface CreateEventUseCase {

    suspend operator fun invoke(eventInfo: EventInfo): Long
}
