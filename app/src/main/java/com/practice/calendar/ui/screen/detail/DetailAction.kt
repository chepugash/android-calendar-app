package com.practice.calendar.ui.screen.detail

sealed interface DetailAction {

    object NavigateBack : DetailAction

    data class ShowToast(val message: String) : DetailAction
}