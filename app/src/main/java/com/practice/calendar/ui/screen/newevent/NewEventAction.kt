package com.practice.calendar.ui.screen.newevent

import com.practice.calendar.ui.screen.calendar.CalendarAction

sealed interface NewEventAction {

    data class NavigateDetail(val eventId: Long) : NewEventAction
}