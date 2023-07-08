package com.practice.calendar.ui.screen.newevent

import androidx.compose.runtime.Immutable
import com.practice.calendar.domain.entity.EventInfo

@Immutable
data class NewEventState(
    val eventInfo: EventInfo? = null,
    val showDateDialog: Boolean = false,
    val showTimeStartDialog: Boolean = false,
    val showTimeFinishDialog: Boolean = false,
    val error: String? = null
)