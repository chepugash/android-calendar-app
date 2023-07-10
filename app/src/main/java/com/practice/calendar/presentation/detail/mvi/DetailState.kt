package com.practice.calendar.presentation.detail.mvi

import androidx.compose.runtime.Immutable
import com.practice.calendar.domain.entity.EventInfo

@Immutable
data class DetailState(
    val eventInfo: EventInfo? = null,
)