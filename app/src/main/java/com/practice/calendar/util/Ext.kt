package com.practice.calendar.util

import android.content.Context
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val TIME_FORMAT = "HH:mm"
const val DATE_FORMAT = "d MMM, yyyy"

fun Context.getJsonFileFromAssets(fileName: String): String? {
    val jsonString: String
    try {
        jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
    return jsonString
}

fun LocalDateTime.formatToTime(): String {
    return this.format(DateTimeFormatter.ofPattern(TIME_FORMAT))
}

fun LocalDateTime.formatToDate(): String {
    return this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
}