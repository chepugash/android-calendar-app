package com.practice.calendar.presentation.navigation

sealed class DestinationScreen(val route: String) {

    object CalendarScreen : DestinationScreen(route = "calendar_screen")

    object DetailScreen : DestinationScreen(route = "detail_screen")

    object NewEventScreen : DestinationScreen(route = "new_event_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}