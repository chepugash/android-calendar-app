package com.practice.calendar.domain.entity

import java.time.LocalDateTime

data class EventInfo(
    val id: Long,
    val dateStart: LocalDateTime,
    val dateFinish: LocalDateTime,
    val name: String,
    val description: String
)