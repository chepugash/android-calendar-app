package com.practice.calendar.data.remote

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.calendar.data.remote.response.EventResponseEntity
import com.practice.calendar.util.getJsonFileFromAssets
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileReader
import javax.inject.Inject

class EventApiImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : EventApi {

    override suspend fun getEvents(): List<EventResponseEntity> {
        val jsonString = appContext.getJsonFileFromAssets(FILE_NAME)
        val gson = Gson()
        val listEventType = object : TypeToken<List<EventResponseEntity>>() {}.type
        return gson.fromJson(jsonString, listEventType)
    }

    companion object {
        private const val FILE_NAME = "event_data.json"
    }
}