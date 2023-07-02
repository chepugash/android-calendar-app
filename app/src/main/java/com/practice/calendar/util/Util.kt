package com.practice.calendar.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun timestampToLocalDateTime(timestamp: Long): LocalDateTime {
    val instant = Instant.ofEpochMilli(timestamp)
    return LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun localDateTimeToTimestamp(ldt: LocalDateTime): Long {
    return ldt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}