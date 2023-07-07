package com.practice.calendar.ui.navigation

sealed class DestinationScreen(val route: String) {

    object CalendarScreen : DestinationScreen(route = "calendar_screen")

    object DetailScreen : DestinationScreen(route = "detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}