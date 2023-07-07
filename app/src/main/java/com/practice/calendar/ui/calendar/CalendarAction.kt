package com.practice.calendar.ui.calendar

sealed interface CalendarAction {

    data class NavigateDetail(val eventId: Long) : CalendarAction
}