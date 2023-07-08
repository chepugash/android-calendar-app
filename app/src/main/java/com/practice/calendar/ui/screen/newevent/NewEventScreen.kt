package com.practice.calendar.ui.screen.newevent

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.calendar.R
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.ui.screen.calendar.CalendarEffect
import com.practice.calendar.ui.theme.CalendarTheme
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun NewEventScreen(
    viewModel: NewEventViewModel = koinViewModel()
) {
    CalendarTheme {
        NewEventContent()
    }
}

@Composable
private fun NewEventContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val info = EventInfo(
            id = 0,
            name = "sample",
            description = "desc sample",
            dateStart = LocalDateTime.now(),
            dateFinish = LocalDateTime.now()
        )
        if (info != null) {
            NewEventToolbar(title = "Новое событие")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Добавьте название:",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                EventTitle(title = info.name)
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                EventTime(
                    start = info.dateStart.formatToTime(),
                    finish = info.dateFinish.formatToTime()
                )
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                EventDate(
                    date = info.dateStart.formatToDate()
                )
                Divider(modifier = Modifier.padding(vertical = 16.dp))
                Text(
                    text = "Добавьте описание:",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
                EventDescription(desc = info.description)
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Применить",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewEventToolbar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_back),
                contentDescription = "toolbar back icon",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        },
    )
}

@Composable
private fun EventTitle(title: String) {
    val textState = remember { mutableStateOf("") }
    val maxSize = 30
    Box {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                if (it.length <= maxSize) {
                    textState.value = it
                }
            },
            textStyle = TextStyle(fontSize = 18.sp),
//            placeholder = {
//                Text(text = "Название", fontSize = 14.sp)
//            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_edit),
                    contentDescription = "title text edit icon",
                )
            },
            trailingIcon = {
                Text(text = "${textState.value.length}/$maxSize")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EventTime(start: String, finish: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_time),
            contentDescription = "toolbar calendar icon in details",
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TimePick()
            Text(
                text = "-",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            TimePick()
        }
    }
}

@Composable
private fun TimePick() {
    val localTime = remember { mutableStateOf(LocalTime.now()) }
    val dateDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        TextButton(
            onClick = {
                dateDialogState.show()
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = localTime.value.formatToTime(),
                fontSize = 20.sp,
            )
        }
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "ок")
                negativeButton(text = "закрыть") {

                }
            },
            onCloseRequest = {

            },
            autoDismiss = true
        ) {
            timepicker(
                initialTime = localTime.value,
                title = "Pick a date",
            ) {
                localTime.value = it
            }
        }
    }
}

@Composable
private fun EventDate(date: String) {
    val localDate = remember { mutableStateOf(LocalDate.now()) }
    val dateDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_calendar),
            contentDescription = "toolbar calendar icon in details",
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
        )
        TextButton(
            onClick = {
                dateDialogState.show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = localDate.value.formatToDate(),
                fontSize = 20.sp,
            )
        }
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "ок")
                negativeButton(text = "закрыть") {

                }
            },
            onCloseRequest = {

            },
            autoDismiss = true
        ) {
            datepicker(
                initialDate = localDate.value,
                title = "Pick a date",
            ) {
                localDate.value = it
            }
        }
    }
}

@Composable
private fun EventDescription(desc: String) {
    val textState = remember { mutableStateOf("") }
    Box {
        OutlinedTextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
            },
            textStyle = TextStyle(fontSize = 18.sp),
//            placeholder = {
//                Text(text = "Описание:", fontSize = 14.sp)
//            },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_description),
                    contentDescription = "title text edit icon",
                )
            },
            minLines = 4,
            maxLines = 14,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun NewEventPreview() {
    CalendarTheme {
        NewEventContent()
    }
}