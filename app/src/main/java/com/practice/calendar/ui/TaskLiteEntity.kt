package com.practice.calendar.ui

data class TaskLiteEntity(
    val time: String,
    val name: String,
)

val tasks: List<TaskLiteEntity> = arrayListOf(
    TaskLiteEntity("08:00", "example1"),
    TaskLiteEntity("09:00", "example2"),
    TaskLiteEntity("10:00", "example3"),
    TaskLiteEntity("11:00", "example4"),
    TaskLiteEntity("12:00", "example5"),
    TaskLiteEntity("13:00", "example6"),
    TaskLiteEntity("14:00", "example7"),
    TaskLiteEntity("15:00", "example8"),
    TaskLiteEntity("16:00", "example9"),
    TaskLiteEntity("17:00", "example10"),
    TaskLiteEntity("08:00", "example1"),
    TaskLiteEntity("09:00", "example2"),
    TaskLiteEntity("10:00", "example3"),
    TaskLiteEntity("11:00", "example4"),
    TaskLiteEntity("12:00", "example5"),
    TaskLiteEntity("13:00", "example6"),
    TaskLiteEntity("14:00", "example7"),
    TaskLiteEntity("15:00", "example8"),
    TaskLiteEntity("16:00", "example9"),
    TaskLiteEntity("17:00", "example10"),
)
