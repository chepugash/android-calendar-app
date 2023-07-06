package com.practice.calendar.ui.calendar

sealed interface CalendarEffect {

    object OnConfirmDialog : CalendarEffect
}