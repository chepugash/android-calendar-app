package com.practice.calendar.presentation.entity

import java.time.LocalDateTime

class EventPresentationEntity(
    val id: Long,
    val dateStart: LocalDateTime,
    val dateFinish: LocalDateTime,
    val name: String,
    val description: String
)
