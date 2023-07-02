package com.practice.calendar.data.remote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.calendar.data.remote.response.EventResponseEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileReader
import javax.inject.Inject

class EventApiImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : EventApi {

    override fun getEvents(): List<EventResponseEntity> {
        val gson = Gson()
        val listEventType = object : TypeToken<List<EventResponseEntity>>() {}.type
        return gson.fromJson(FileReader("event_data.json"), listEventType)
    }
}