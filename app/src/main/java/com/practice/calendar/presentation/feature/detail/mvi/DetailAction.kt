package com.practice.calendar.presentation.feature.detail.mvi

sealed interface DetailAction {

    object NavigateBack : DetailAction

    class ShowToast(val message: String) : DetailAction
}
