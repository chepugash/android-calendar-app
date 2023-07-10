package com.practice.calendar.presentation.feature.newevent.mvi

sealed interface NewEventAction {

    class NavigateToDetail(val eventId: Long) : NewEventAction

    object NavigateBack : NewEventAction

    class ShowToast(val message: String) : NewEventAction
}
