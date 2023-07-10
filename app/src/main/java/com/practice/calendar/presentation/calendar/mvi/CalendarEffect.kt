package com.practice.calendar.presentation.calendar.mvi

import java.time.LocalDate

sealed interface CalendarEffect {

    object OnDateClick : CalendarEffect

    data class OnConfirmDialog(val date: LocalDate) : CalendarEffect

    object OnCloseDialog : CalendarEffect

    data class OnEventClick(val eventId: Long) : CalendarEffect

    object OnAddEventClick : CalendarEffect
}