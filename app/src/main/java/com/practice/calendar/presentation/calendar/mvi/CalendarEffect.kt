package com.practice.calendar.presentation.calendar.mvi

import java.time.LocalDate

sealed interface CalendarEffect {

    object OnDateClick : CalendarEffect

    class OnConfirmDialog(val date: LocalDate) : CalendarEffect

    object OnCloseDialog : CalendarEffect

    class OnEventClick(val eventId: Long) : CalendarEffect

    object OnAddEventClick : CalendarEffect
}