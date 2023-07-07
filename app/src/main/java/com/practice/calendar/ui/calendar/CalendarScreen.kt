@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.practice.calendar.ui.calendar

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.practice.calendar.R
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.ui.theme.CalendarTheme
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import com.practice.calendar.util.timeInMinutes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    CalendarContent(viewState = state.value, eventHandler = viewModel::effect)

    CalendarScreenActions()
}

@Composable
fun CalendarContent(
    viewState: CalendarState,
    eventHandler: (CalendarEffect) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarToolbar(
            viewState = viewState,
            eventHandler = eventHandler
        )
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
                EventList(viewState = viewState)
            }
        }
    }
}

@Composable
private fun CalendarScreenActions() {}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarToolbar(viewState: CalendarState, eventHandler: (CalendarEffect) -> Unit) {
    TopAppBar(
        title = {
            DatePicker(
                viewState = viewState,
                eventHandler = eventHandler
            )
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = "toolbar calendar icon",
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }
    )
}

@Composable
fun DatePicker(
    viewState: CalendarState,
    eventHandler: (CalendarEffect) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()
    Row {
        TextButton(onClick = {
            eventHandler.invoke(CalendarEffect.OnDateClick)
        }) {
            Text(
                text = viewState.date.formatToDate(),
                fontSize = 24.sp,
                color = Color.Blue
            )
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "ок")
            negativeButton(text = "закрыть") {
                eventHandler.invoke(CalendarEffect.OnCloseDialog)
            }
        },
        onCloseRequest = {
            eventHandler.invoke(CalendarEffect.OnCloseDialog)
        },
        autoDismiss = false
    ) {
        datepicker(
            initialDate = viewState.date,
            title = "Pick a date",
        ) {
            eventHandler.invoke(CalendarEffect.OnConfirmDialog(it))
        }
    }
    if (viewState.showDialog) { dateDialogState.show() } else { dateDialogState.hide() }
}

@Composable
fun EventList(
    viewState: CalendarState,
) {
    if (viewState.eventInfoList != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            repeat(viewState.eventInfoList.size) {index ->
                EventCard(viewState.eventInfoList[index])
            }
        }
    }
}

@Composable
fun EventCard(eventInfo: EventInfo) {
    val minuteStart = eventInfo.dateStart.timeInMinutes()
    val minuteFinish = eventInfo.dateFinish.timeInMinutes()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = minuteStart.dp + 30.dp)
            .height((minuteFinish - minuteStart).dp)
            .padding(start = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { }
    ) {
        Column{
            Text(text = eventInfo.name)
            Text(
                text = "${eventInfo.dateStart.formatToTime()} - ${eventInfo.dateFinish.formatToTime()}"
            )
        }
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
private fun CalendarPreview() {
    CalendarTheme {
        CalendarScreen()
    }
}
