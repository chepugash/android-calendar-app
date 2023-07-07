package com.practice.calendar.ui.detail

import androidx.compose.runtime.Immutable
import com.practice.calendar.domain.entity.EventInfo

@Immutable
data class DetailState(
    val eventInfo: EventInfo? = null,
    val error: String? = null
)