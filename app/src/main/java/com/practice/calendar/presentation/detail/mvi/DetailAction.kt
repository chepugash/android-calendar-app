package com.practice.calendar.presentation.detail.mvi

sealed interface DetailAction {

    object NavigateBack : DetailAction

    class ShowToast(val message: String) : DetailAction
}