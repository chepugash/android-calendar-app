package com.practice.calendar.data.remote

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.practice.calendar.data.remote.response.EventResponseEntity
import java.io.IOException

class EventApiImpl(
    private val appContext: Context
) : EventApi {

    override suspend fun getEvents(): List<EventResponseEntity> {
        val jsonString = appContext.getJsonFileFromAssets(FILE_NAME)
        val gson = Gson()
        val listEventType = object : TypeToken<List<EventResponseEntity>>() {}.type
        return gson.fromJson(jsonString, listEventType)
    }

    private fun Context.getJsonFileFromAssets(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    companion object {
        private const val FILE_NAME = "event_data.json"
    }
}
