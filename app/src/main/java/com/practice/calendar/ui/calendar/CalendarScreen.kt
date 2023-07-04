@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.practice.calendar.ui.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.calendar.R
import com.practice.calendar.ui.theme.CalendarTheme
import com.practice.calendar.util.formatToDate
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun CalendarScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarToolbar()
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .height(1440.dp + 30.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HoursList()
            Box {
                TimeTable()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    EventCard(120, 150)
                    EventCard(500, 800)
                    EventCard(1000, 1100)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarToolbar() {
    TopAppBar(
        title = {
            DatePicker()
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = "toolbar calendar icon",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        },
    )
}

@Composable
fun DatePicker() {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val dateDialogState = rememberMaterialDialogState()
    Row {
        TextButton(onClick = {
            dateDialogState.show()
        }) {
            Text(
                text = pickedDate.formatToDate(),
                fontSize = 24.sp,
                color = Color.Blue
            )
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Pick a date"
        ) {
            pickedDate = it
        }
    }
}

@Composable
fun EventCard(minuteStart: Int, minuteFinish: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp)
            .offset(y = minuteStart.dp + 30.dp)
            .height((minuteFinish - minuteStart).dp)
    ) {
        Text(
            text = "$minuteStart - $minuteFinish"
        )
    }
}

@Composable
fun HoursList() {
    LazyColumn(
        modifier = Modifier
            .wrapContentWidth()
            .height(1440.dp + 30.dp)
    ) {
        items(24) { index ->
            Box(
                modifier = Modifier
                    .height(60.dp)
            ) {
                val formattedIndex = if (index < 10) {
                    "0$index:00"
                } else {
                    "$index:00"
                }
                Text(
                    text = formattedIndex,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}

@Composable
fun TimeTable() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
            .height(1440.dp + 30.dp)
    ) {
        items(24) { _ ->
            Box(
                modifier = Modifier
                    .height(60.dp)
            ) {
                Divider(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    CalendarTheme {
        CalendarScreen()
    }
}
