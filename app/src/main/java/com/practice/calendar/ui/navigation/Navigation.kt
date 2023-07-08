package com.practice.calendar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.practice.calendar.ui.screen.calendar.CalendarScreen
import com.practice.calendar.ui.screen.detail.DetailScreen
import com.practice.calendar.ui.screen.newevent.NewEventScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationScreen.CalendarScreen.route
    ) {

        composable(route = DestinationScreen.CalendarScreen.route) {
            CalendarScreen(navController = navController)
        }

        composable(
            route = DestinationScreen.DetailScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { entry ->
            entry.arguments?.getLong("id")?.let {
                DetailScreen(eventId = it, navController = navController)
            }
        }

        composable(route = DestinationScreen.NewEventScreen.route) {
            NewEventScreen()
        }
    }
}