package com.practice.calendar.ui.calendar

import java.time.LocalDate

sealed interface CalendarEffect {

    object OnDateClick : CalendarEffect

    data class OnConfirmDialog(val date: LocalDate) : CalendarEffect

    object OnCloseDialog : CalendarEffect
}