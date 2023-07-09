package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow

interface GetEventUseCase {

    operator fun invoke(eventId: Long): Flow<EventInfo?>
}