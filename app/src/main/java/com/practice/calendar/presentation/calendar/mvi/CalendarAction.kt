package com.practice.calendar.presentation.calendar.mvi

sealed interface CalendarAction {

    class NavigateDetail(val eventId: Long) : CalendarAction

    object NavigateAddEvent : CalendarAction

    class ShowToast(val message: String) : CalendarAction
}