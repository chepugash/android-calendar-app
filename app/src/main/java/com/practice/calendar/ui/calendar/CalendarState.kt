package com.practice.calendar.ui.calendar

import com.practice.calendar.domain.entity.EventInfo

data class CalendarState(
    val eventInfoList: List<EventInfo>? = null,
    val error: String? = null
)