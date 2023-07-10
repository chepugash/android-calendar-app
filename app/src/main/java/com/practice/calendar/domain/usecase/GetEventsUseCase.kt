package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import kotlinx.coroutines.flow.Flow

interface GetEventsUseCase {

    operator fun invoke(date: Long): Flow<List<EventInfo>?>
}
