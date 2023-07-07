package com.practice.calendar.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.practice.calendar.ui.calendar.CalendarScreen
import com.practice.calendar.ui.navigation.Navigation
import com.practice.calendar.ui.theme.CalendarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                Navigation()
            }
        }
    }
}