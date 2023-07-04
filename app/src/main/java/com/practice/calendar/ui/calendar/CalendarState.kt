package com.practice.calendar.ui.calendar

import androidx.compose.runtime.Immutable
import com.practice.calendar.domain.entity.EventInfo
import kotlinx.collections.immutable.PersistentList

@Immutable
data class CalendarState(
    val eventInfoList: PersistentList<EventInfo>? = null,
    val error: String? = null
)