package com.practice.calendar.presentation.feature.detail.mvi

import androidx.compose.runtime.Immutable
import com.practice.calendar.presentation.entity.EventPresentationEntity

@Immutable
data class DetailState(
    val eventEntity: EventPresentationEntity? = null,
)
