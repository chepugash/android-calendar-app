package com.practice.calendar.ui.screen.newevent

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.calendar.R
import com.practice.calendar.ui.navigation.DestinationScreen
import com.practice.calendar.ui.theme.CalendarTheme
import com.practice.calendar.ui.theme.LightOnPrimary
import com.practice.calendar.ui.theme.LightPrimary
import com.practice.calendar.ui.theme.LightPrimaryContainer
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun NewEventScreen(
    viewModel: NewEventViewModel = koinViewModel(),
    navController: NavController
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    NewEventContent(
        viewState = state.value,
        effectHandler = viewModel::effect
    )

    NewEventScreenActions(
        navController = navController,
        viewAction = action
    )

    BackHandler {
        viewModel::effect.invoke(NewEventEffect.OnBackClick)
    }
}

@Composable
private fun NewEventScreenActions(
    navController: NavController,
    viewAction: NewEventAction?,
) {
    val context = LocalContext.current
    LaunchedEffect(viewAction) {
        when (viewAction) {
            null -> Unit
            is NewEventAction.NavigateToDetail -> {
                navController.navigate(
                    DestinationScreen.DetailScreen.withArgs(viewAction.eventId.toString())
                )
            }

            is NewEventAction.ShowToast -> {
                Toast.makeText(context, viewAction.message, Toast.LENGTH_LONG).show()
            }

            NewEventAction.NavigateBack -> navController.popBackStack()
        }
    }
}

@Composable
private fun NewEventContent(
    viewState: NewEventState,
    effectHandler: (NewEventEffect) -> Unit
) {
    Scaffold(
        topBar = {
            NewEventToolbar(effectHandler)
        },
    ) {
        it.calculateTopPadding()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.step4),
                    end = dimensionResource(id = R.dimen.step4),
                    bottom = dimensionResource(id = R.dimen.step4),
                    top = it.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {
            EventTitle(
                name = viewState.name,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventTime(
                viewState = viewState,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventDate(
                date = viewState.date,
                showDialog = viewState.showDateDialog,
                effectHandler = effectHandler
            )
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventDescription(
                desc = viewState.description,
                effectHandler = effectHandler
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Button(
                    onClick = {
                        effectHandler.invoke(NewEventEffect.OnConfirmClick)
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(top = dimensionResource(id = R.dimen.step2))
                ) {
                    Text(
                        text = stringResource(R.string.new_event_screen_confirm_button),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewEventToolbar(
    effectHandler: (NewEventEffect) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.new_event),
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    effectHandler.invoke(NewEventEffect.OnBackClick)
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.toolbar_back_icon_in_new_event),
                )
            }
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
            text = stringResource(R.string.new_event_add_name),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.step2))
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
                    contentDescription = stringResource(R.string.title_text_edit_icon),
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
            contentDescription = stringResource(R.string.time_icon_in_new_event),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.step2))
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
                text = stringResource(R.string.time_divider),
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
                positiveButton(text = stringResource(R.string.date_picker_positive))
                negativeButton(text = stringResource(R.string.date_picker_negative)) {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeStartDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseTimeStartDialog)
            },
            autoDismiss = false,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            timepicker(
                initialTime = timeStart,
                title = stringResource(R.string.start_time_picker_title),
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    activeTextColor = MaterialTheme.colorScheme.onPrimary,
                    activeBackgroundColor = MaterialTheme.colorScheme.primary,
                    headerTextColor = MaterialTheme.colorScheme.onBackground,
                    selectorTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectorColor = MaterialTheme.colorScheme.primary,
                    inactivePeriodBackground = MaterialTheme.colorScheme.primaryContainer,
                    inactiveTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    inactiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                )
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmTimeStartDialog(it))
            }
        }
    }
    if (showDialog) {
        timeDialogState.show()
    } else {
        timeDialogState.hide()
    }
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
                positiveButton(text = stringResource(R.string.date_picker_positive))
                negativeButton(text = stringResource(R.string.date_picker_negative)) {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeFinishDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseTimeFinishDialog)
            },
            autoDismiss = false,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            timepicker(
                initialTime = timeFinish,
                title = stringResource(R.string.finish_time_picker_time),
                is24HourClock = true,
                colors = TimePickerDefaults.colors(
                    activeTextColor = MaterialTheme.colorScheme.onPrimary,
                    activeBackgroundColor = MaterialTheme.colorScheme.primary,
                    headerTextColor = MaterialTheme.colorScheme.onBackground,
                    selectorTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectorColor = MaterialTheme.colorScheme.primary,
                    inactivePeriodBackground = MaterialTheme.colorScheme.primaryContainer,
                    inactiveTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    inactiveBackgroundColor = MaterialTheme.colorScheme.primaryContainer,
                )
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmTimeFinishDialog(it))
            }
        }
    }
    if (showDialog) {
        timeDialogState.show()
    } else {
        timeDialogState.hide()
    }
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
            contentDescription = stringResource(R.string.calendar_icon_in_new_event),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.step2))
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
                positiveButton(text = stringResource(R.string.date_picker_positive))
                negativeButton(text = stringResource(R.string.date_picker_negative)) {
                    effectHandler.invoke(NewEventEffect.OnCloseDateDialog)
                }
            },
            onCloseRequest = {
                effectHandler.invoke(NewEventEffect.OnCloseDateDialog)
            },
            autoDismiss = false,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            datepicker(
                initialDate = date,
                title = stringResource(R.string.date_picker_title),
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = MaterialTheme.colorScheme.primary,
                    headerTextColor = MaterialTheme.colorScheme.onPrimary,
                    calendarHeaderTextColor = MaterialTheme.colorScheme.primary,
                    dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                    dateActiveTextColor = MaterialTheme.colorScheme.onPrimary,
                    dateInactiveTextColor = MaterialTheme.colorScheme.onBackground,
                )
            ) {
                effectHandler.invoke(NewEventEffect.OnConfirmDateDialog(it))
            }
        }
    }
    if (showDialog) {
        dateDialogState.show()
    } else {
        dateDialogState.hide()
    }
}

@Composable
private fun EventDescription(
    desc: String,
    effectHandler: (NewEventEffect) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.new_event_add_description),
            fontSize = 18.sp,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.step2))
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
                    contentDescription = stringResource(R.string.description_edit_icon_in_new_event),
                )
            },
            minLines = 4,
            maxLines = 14,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
