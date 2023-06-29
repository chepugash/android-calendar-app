@file:OptIn(ExperimentalMaterial3Api::class)

package com.practice.calendar.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.calendar.R
import com.practice.calendar.ui.theme.CalendarTheme

@Composable
fun CalendarScreen() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarToolbar()
        TaskItem(time = "14:00", name = "Call John")
    }
}

@Composable
fun CalendarToolbar() {
    TopAppBar(
        title = {
            Text(text = "26, июнь 2023")
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = "toolbar calendar icon")
        }
    )
}

@Composable
fun TaskTable() {
    LazyColumn {

    }
}

@Composable
fun TaskItem(time: String, name: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = time,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(text = name)

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalendarTheme {
        CalendarToolbar()
    }
}
