package com.practice.calendar.data.repository

import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.remote.EventApi
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val api: EventApi,
    private val dao: EventDao
) {
}