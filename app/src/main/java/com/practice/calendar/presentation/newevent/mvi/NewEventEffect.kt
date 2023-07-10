package com.practice.calendar.presentation.newevent.mvi

import java.time.LocalDate
import java.time.LocalTime

sealed interface NewEventEffect {

    object OnDateClick : NewEventEffect
    class OnConfirmDateDialog(val date: LocalDate) : NewEventEffect
    object OnCloseDateDialog : NewEventEffect

    object OnTimeStartClick : NewEventEffect
    class OnConfirmTimeStartDialog(val timeStart: LocalTime) : NewEventEffect
    object OnCloseTimeStartDialog : NewEventEffect

    object OnTimeFinishClick : NewEventEffect
    class OnConfirmTimeFinishDialog(val timeFinish: LocalTime) : NewEventEffect
    object OnCloseTimeFinishDialog : NewEventEffect

    class OnNameChanged(val name: String) : NewEventEffect

    class OnDescriptionChanged(val description: String) : NewEventEffect

    object OnConfirmClick : NewEventEffect

    object OnBackClick : NewEventEffect

}