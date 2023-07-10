package com.practice.calendar.presentation.detail.mvi

sealed interface DetailEffect {

    data class ShowEvent(val eventId: Long) : DetailEffect

    data class OnDeleteClick(val eventId: Long) : DetailEffect

    object OnBackClick : DetailEffect
}