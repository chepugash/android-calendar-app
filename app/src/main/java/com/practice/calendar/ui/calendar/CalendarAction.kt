package com.practice.calendar.ui.calendar

sealed interface CalendarAction {

    object ShowDialog : CalendarAction
}