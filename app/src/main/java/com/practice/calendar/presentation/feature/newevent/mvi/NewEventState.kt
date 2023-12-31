package com.practice.calendar.presentation.feature.newevent.mvi

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.LocalTime

@Immutable
data class NewEventState(
    val name: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val timeStart: LocalTime = LocalTime.now(),
    val timeFinish: LocalTime = timeStart.plusHours(1),
    val showDateDialog: Boolean = false,
    val showTimeStartDialog: Boolean = false,
    val showTimeFinishDialog: Boolean = false,
)
