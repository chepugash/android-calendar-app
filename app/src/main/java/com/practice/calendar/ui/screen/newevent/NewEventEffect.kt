package com.practice.calendar.ui.screen.newevent

import java.time.LocalDate
import java.time.LocalTime

sealed interface NewEventEffect {

    object OnDateClick : NewEventEffect
    data class OnConfirmDateDialog(val date: LocalDate) : NewEventEffect
    object OnCloseDateDialog : NewEventEffect

    object OnTimeStartClick : NewEventEffect
    data class OnConfirmTimeStartDialog(val timeStart: LocalTime) : NewEventEffect
    object OnCloseTimeStartDialog : NewEventEffect

    object OnTimeFinishClick : NewEventEffect
    data class OnConfirmTimeFinishDialog(val timeFinish: LocalTime) : NewEventEffect
    object OnCloseTimeFinishDialog : NewEventEffect

    data class ShowEvent(val eventId: Long) : NewEventEffect

    data class OnNameChanged(val name: String) : NewEventEffect

    data class OnDescriptionChanged(val desc: String) : NewEventEffect
}