package com.practice.calendar.domain.usecase

interface DeleteEventUseCase {

    suspend operator fun invoke(eventId: Long)
}
