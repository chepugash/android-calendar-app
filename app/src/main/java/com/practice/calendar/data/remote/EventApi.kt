package com.practice.calendar.data.remote

import com.practice.calendar.data.remote.response.EventResponseEntity

// actually it's not remote, just an imitation
interface EventApi {

    suspend fun getEvents() : List<EventResponseEntity>
}