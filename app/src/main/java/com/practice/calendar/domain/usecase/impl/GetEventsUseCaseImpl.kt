package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetEventsUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventsUseCase {

    override operator fun invoke(date: LocalDate): Flow<List<List<EventInfo>>?> {
        return eventRepository.getEventsByDate(date).groupByTime()
    }

    /*
    Все вот эти танцы с бубнами нужны для того, чтобы если события пересекались
    по времени, то показывались в одной строчке, а не наслаивались друг на друга.
    В дата слое я это мапить не стал потому что думаю не место там этому
    Тут оставлять тоже как то странно
    Д и вообще опять матрешка ебучая с этими списками в списках в флоу
    */
    private fun Flow<List<EventInfo>?>.groupByTime(): Flow<List<List<EventInfo>>?> {
        return this.map {list ->
            if (list != null) {
                val intersectingEvents = mutableListOf<List<EventInfo>>()
                val visited = BooleanArray(list.size) { false }

                for (i in list.indices) {
                    if (!visited[i]) {
                        val intersectingEvent = mutableListOf<EventInfo>()
                        intersectingEvent.add(list[i])
                        visited[i] = true

                        for (j in i + 1 until list.size) {
                            if (!visited[j] && isIntersecting(list[i], list[j])) {
                                intersectingEvent.add(list[j])
                                visited[j] = true
                            }
                        }

                        intersectingEvents.add(intersectingEvent)
                    }
                }

                return@map intersectingEvents
            } else {
                return@map null
            }
        }
    }

    private fun isIntersecting(event1: EventInfo, event2: EventInfo): Boolean {
        return event1.dateStart <= event2.dateFinish && event2.dateStart <= event1.dateFinish
    }
}