package com.practice.calendar.presentation.calendar.mvi

sealed interface CalendarAction {

    data class NavigateDetail(val eventId: Long) : CalendarAction

    object NavigateAddEvent : CalendarAction

    data class ShowToast(val message: String) : CalendarAction
}