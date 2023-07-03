package com.practice.calendar.util

import android.content.Context
import java.io.IOException

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