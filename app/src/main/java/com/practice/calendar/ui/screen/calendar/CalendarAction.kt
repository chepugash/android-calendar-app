package com.practice.calendar.ui.screen.calendar

sealed interface CalendarAction {

    data class NavigateDetail(val eventId: Long) : CalendarAction

    object NavigateAddEvent : CalendarAction
}