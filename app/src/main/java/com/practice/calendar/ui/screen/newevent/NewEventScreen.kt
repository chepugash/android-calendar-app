package com.practice.calendar.ui.screen.newevent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    NewEventContent(
        viewState = state.value,
        effectHandler = viewModel::effect
    )
}

@Composable
private fun NewEventContent(
    viewState: NewEventState,
    effectHandler: (NewEventEffect) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NewEventToolbar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            EventTitle(
                name = viewState.name,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            EventTime(
                viewState = viewState,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            EventDate(
                date = viewState.date,
                showDialog = viewState.showDateDialog,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = 16.dp))

            EventDescription(
                desc = viewState.description,
                effectHandler = effectHandler
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewEventToolbar() {
    TopAppBar(
        title = {
            Text(
                text = "Новое событие",
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
private fun EventTitle(
    name: String,
    effectHandler: (NewEventEffect) -> Unit
) {
    val maxSize = 30
    Column {
        Text(
            text = "Добавьте название:",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = name,
            onValueChange = {
                if (it.length <= maxSize) {
                    effectHandler.invoke(NewEventEffect.OnNameChanged(it))
                }
            },
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_edit),
                    contentDescription = "title text edit icon",
                )
            },
            trailingIcon = {
                Text(text = "${name.length}/$maxSize")
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun EventTime(
    viewState: NewEventState,
    effectHandler: (NewEventEffect) -> Unit
) {
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
            TimeStartPicker(
                timeStart = viewState.timeStart,
                showDialog = viewState.showTimeStartDialog,
                effectHandler = effectHandler
            )
            Text(
                text = "-",
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            TimeFinishPicker(
                timeFinish = viewState.timeFinish,
                showDialog = viewState.showTimeFinishDialog,
                effectHandler = effectHandler
            )
        }
    }
}

@Composable
private fun TimeStartPicker(
    timeStart: LocalTime,
    showDialog: Boolean,
    effectHandler: (NewEventEffect) -> Unit
) {
    val timeDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        TextButton(
            onClick = {
                effectHandler.invoke(NewEventEffect.OnTimeStartClick)
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = timeStart.formatToTime(),
                fontSize = 20.sp,
            )
        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "ок")
                negativeButton(text = "закрыть") {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeStartDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseTimeStartDialog)
            },
            autoDismiss = false,

        ) {
            timepicker(
                initialTime = timeStart,
                title = "Выберите время начала",
                is24HourClock = true
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmTimeStartDialog(it))
            }
        }
    }
    if (showDialog) { timeDialogState.show() } else { timeDialogState.hide() }
}

@Composable
private fun TimeFinishPicker(
    timeFinish: LocalTime,
    showDialog: Boolean,
    effectHandler: (NewEventEffect) -> Unit
) {
    val timeDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        TextButton(
            onClick = {
                effectHandler.invoke(NewEventEffect.OnTimeFinishClick)
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = timeFinish.formatToTime(),
                fontSize = 20.sp,
            )
        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(text = "ок")
                negativeButton(text = "закрыть") {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeFinishDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseTimeFinishDialog)
            },
            autoDismiss = true
        ) {
            timepicker(
                initialTime = timeFinish,
                title = "Выберите время окончания",
                is24HourClock = true
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmTimeFinishDialog(it))
            }
        }
    }
    if (showDialog) { timeDialogState.show() } else { timeDialogState.hide() }
}

@Composable
private fun EventDate(
    date: LocalDate,
    showDialog: Boolean,
    effectHandler: (NewEventEffect) -> Unit
) {
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
                effectHandler.invoke(NewEventEffect.OnDateClick)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = date.formatToDate(),
                fontSize = 20.sp,
            )
        }
        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(text = "ок")
                negativeButton(text = "закрыть") {
                    effectHandler.invoke(NewEventEffect.OnCloseDateDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseDateDialog)
            },
            autoDismiss = false
        ) {
            datepicker(
                initialDate = date,
                title = "Выберите дату",
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmDateDialog(it))
            }
        }
    }
    if (showDialog) { dateDialogState.show() } else { dateDialogState.hide() }
}

@Composable
private fun EventDescription(
    desc: String,
    effectHandler: (NewEventEffect) -> Unit
) {
    Column {
        Text(
            text = "Добавьте описание:",
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = desc,
            onValueChange = {
                effectHandler.invoke(NewEventEffect.OnDescriptionChanged(it))
            },
            textStyle = TextStyle(fontSize = 18.sp),
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_description),
                    contentDescription = "title description edit icon",
                )
            },
            minLines = 4,
            maxLines = 14,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
