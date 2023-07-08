package com.practice.calendar.util

import android.content.Context
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

const val TIME_FORMAT = "HH:mm"
const val DATE_FORMAT = "d MMM, yyyy"
const val MINUTES_IN_HOUR = 60

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

fun LocalDate.formatToDate(): String {
    return this.format(DateTimeFormatter.ofPattern(DATE_FORMAT))
}

fun LocalTime.formatToTime(): String {
    return this.format(DateTimeFormatter.ofPattern(TIME_FORMAT))
}

fun LocalDateTime.timeInMinutes(): Int {
    return this.hour * MINUTES_IN_HOUR + this.minute
}