package com.practice.calendar.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.practice.calendar.R
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.ui.theme.CalendarTheme
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DetailScreen(
    eventInfo: EventInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DetailToolbar(title = eventInfo.name)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EventTitle(title = eventInfo.name)
            Spacer(modifier = Modifier.padding(8.dp))
            EventTime(
                start = eventInfo.dateStart.formatToTime(),
                finish = eventInfo.dateFinish.formatToTime()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            EventDate(
                date = eventInfo.dateStart.formatToDate()
            )
            Spacer(modifier = Modifier.padding(8.dp))
            EventDescription(desc = eventInfo.description)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(title: String) {
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
fun EventTitle(title: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        Text(
            text = title,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EventTime(start: String, finish: String) {
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
        Text(
            text = start,
            fontSize = 24.sp,
        )
        Text(
            text = "-",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
        )
        Text(
            text = finish,
            fontSize = 24.sp
        )
    }
}

@Composable
fun EventDate(date: String) {
    Row {
        Icon(
            painterResource(id = R.drawable.ic_calendar),
            contentDescription = "toolbar calendar icon in details",
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = date,
            fontSize = 24.sp
        )
    }
}

@Composable
fun EventDescription(desc: String) {
    Box {
        Text(
            text = desc,
            fontSize = 18.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    CalendarTheme {
        DetailScreen(event)
    }
}

val event: EventInfo = EventInfo(
    id = 1,
    dateStart = LocalDateTime.now(),
    dateFinish = LocalDateTime.now(),
    name = "test name",
    description = "test description"
)
