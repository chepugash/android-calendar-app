package com.practice.calendar.util

class EmptyNameException(message: String) : Exception(message)

class TimeFinishLessThanStartException(message: String) : Exception(message)

class TimePeriodLessThanHalfHour(message: String) : Exception(message)
