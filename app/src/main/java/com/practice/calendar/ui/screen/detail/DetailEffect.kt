package com.practice.calendar.ui.screen.detail

sealed interface DetailEffect {

    data class ShowEvent(val eventId: Long) : DetailEffect
}