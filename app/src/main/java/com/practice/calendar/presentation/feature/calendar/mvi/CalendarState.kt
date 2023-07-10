package com.practice.calendar.presentation.feature.calendar.mvi

import androidx.compose.runtime.Immutable
import com.practice.calendar.presentation.entity.EventPresentationEntity
import kotlinx.collections.immutable.PersistentList
import java.time.LocalDate

@Immutable
data class CalendarState(
    val date: LocalDate = LocalDate.now(),
    val eventPresentationEntityList: PersistentList<PersistentList<EventPresentationEntity>>? = null,
    val showDialog: Boolean = false
)
