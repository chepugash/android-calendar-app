package com.practice.calendar.domain.usecase

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.util.EventsGroupedByTime
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface GetEventsUseCase {

    operator fun invoke(date: LocalDate): Flow<List<EventInfo>?>
}