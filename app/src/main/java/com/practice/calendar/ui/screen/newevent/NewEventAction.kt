package com.practice.calendar.ui.screen.newevent

sealed interface NewEventAction {

    data class NavigateToDetail(val eventId: Long) : NewEventAction

    object NavigateBack : NewEventAction

    data class ShowToast(val message: String) : NewEventAction
}