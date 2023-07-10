package com.practice.calendar.ui.screen.calendar

import androidx.compose.runtime.Immutable
import com.practice.calendar.domain.entity.EventInfo
import kotlinx.collections.immutable.PersistentList
import java.time.LocalDate

@Immutable
data class CalendarState(
    val date: LocalDate = LocalDate.now(),
    val eventInfoList: PersistentList<PersistentList<EventInfo>>? = null,
    val showDialog: Boolean = false
)