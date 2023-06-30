@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.practice.calendar.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.practice.calendar.R
import com.practice.calendar.ui.TaskLiteEntity
import com.practice.calendar.ui.tasks
import com.practice.calendar.ui.theme.CalendarTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate

@Composable
fun CalendarScreen() {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        var pickedDate by remember {
            mutableStateOf(LocalDate.now())
        }
        val dateDialogState = rememberMaterialDialogState()
        CalendarToolbar()
        TaskTable(tasks = tasks)
    }
}

@Composable
fun CalendarToolbar() {
    TopAppBar(
        title = {
            DatePicker()
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = "toolbar calendar icon")
        }
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
            Text(text = pickedDate.toString())
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
fun TaskTable(tasks: List<TaskLiteEntity>) {
    LazyColumn(
        modifier = Modifier.fillMaxHeight()
    ) {
        items(tasks.size) {
            TaskItem(task = tasks[it])
        }
    }
}

@Composable
fun TaskItem(task: TaskLiteEntity) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = task.time,
            modifier = Modifier.padding(end = 16.dp)
        )
        Text(text = task.name)

    }
}

@Preview(showBackground = true)
@Composable
fun CalendarPreview() {
    CalendarTheme {
        CalendarScreen()
    }
}
