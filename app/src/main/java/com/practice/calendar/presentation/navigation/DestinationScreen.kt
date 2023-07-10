package com.practice.calendar.presentation.navigation

sealed class DestinationScreen(val route: String) {

    object CalendarScreen : DestinationScreen(route = CALENDAR_ROUTE)

    object DetailScreen : DestinationScreen(route = DETAIL_ROUTE)

    object NewEventScreen : DestinationScreen(route = NEW_EVENT_ROUTE)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    companion object {
        private const val CALENDAR_ROUTE = "calendar_screen"
        private const val DETAIL_ROUTE = "detail_screen"
        private const val NEW_EVENT_ROUTE = "new_event_screen"
    }
}