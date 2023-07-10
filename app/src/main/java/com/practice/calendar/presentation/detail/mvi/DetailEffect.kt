package com.practice.calendar.presentation.detail.mvi

sealed interface DetailEffect {

    class ShowEvent(val eventId: Long) : DetailEffect

    class OnDeleteClick(val eventId: Long) : DetailEffect

    object OnBackClick : DetailEffect
}