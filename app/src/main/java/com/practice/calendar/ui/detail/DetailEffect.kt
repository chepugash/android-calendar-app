package com.practice.calendar.ui.detail

sealed interface DetailEffect {

    data class ShowEvent(val eventId: Long) : DetailEffect
}