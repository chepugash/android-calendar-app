@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.practice.calendar.ui.screen.calendar

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.practice.calendar.R
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.ui.navigation.DestinationScreen
import com.practice.calendar.ui.screen.components.CustomDatePickerDialog
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import com.practice.calendar.util.timeInMinutes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel

private const val HOURS_IN_DAY = 24
private const val COUNT_OF_DIGITS = 10

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel = koinViewModel(),
    navController: NavController
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    CalendarContent(
        viewState = state.value,
        effectHandler = viewModel::effect
    )

    CalendarScreenActions(
        navController = navController,
        viewAction = action,
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun CalendarContent(
    viewState: CalendarState,
    effectHandler: (CalendarEffect) -> Unit
) {
    Scaffold(
        topBar = {
            CalendarToolbar(
                viewState = viewState,
                effectHandler = effectHandler
            )
        },
        floatingActionButton = {
            AddButton(effectHandler = effectHandler)
        },
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.step4))
                    .height(dimensionResource(id = R.dimen.calendar_height))
                    .verticalScroll(rememberScrollState())
            ) {
                HoursList()
                Box {
                    TimeTable()
                    EventList(viewState = viewState, effectHandler = effectHandler)
                }
            }
        }
    }
}

@Composable
private fun CalendarScreenActions(
    navController: NavController,
    viewAction: CalendarAction?,
) {
    val context = LocalContext.current
    LaunchedEffect(viewAction) {
        when (viewAction) {
            null -> Unit
            is CalendarAction.NavigateDetail -> {
                navController.navigate(
                    DestinationScreen.DetailScreen.withArgs(viewAction.eventId.toString())
                )
            }
            is CalendarAction.ShowToast -> {
                Toast.makeText(context, viewAction.message, Toast.LENGTH_LONG).show()
            }
            CalendarAction.NavigateAddEvent -> {
                navController.navigate(
                    DestinationScreen.NewEventScreen.route,
                    navOptions = navOptions { popUpToRoute }
                )
            }
        }
    }
}

@Composable
fun AddButton(effectHandler: (CalendarEffect) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = {
                effectHandler.invoke(CalendarEffect.OnAddEventClick)
            },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(R.string.add_button_icon)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarToolbar(
    viewState: CalendarState,
    effectHandler: (CalendarEffect) -> Unit
) {
    TopAppBar(
        title = {
            DatePicker(
                viewState = viewState,
                effectHandler = effectHandler
            )
        },
        navigationIcon = {
            Icon(
                painterResource(id = R.drawable.ic_calendar),
                contentDescription = stringResource(R.string.toolbar_calendar_icon),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.step4))
            )
        },
    )
}

@Composable
fun DatePicker(
    viewState: CalendarState,
    effectHandler: (CalendarEffect) -> Unit
) {
    val dateDialogState = rememberMaterialDialogState()
    Row {
        TextButton(onClick = {
            effectHandler.invoke(CalendarEffect.OnDateClick)
        }) {
            Text(
                text = viewState.date.formatToDate(),
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = stringResource(R.string.more_icon_in_calendar)
            )
        }
    }
    CustomDatePickerDialog(
        date = viewState.date,
        dialogState = dateDialogState,
        onClose = {
            effectHandler.invoke(CalendarEffect.OnCloseDialog)
        },
        onConfirm = {
            effectHandler.invoke(CalendarEffect.OnConfirmDialog(it))
        }
    )
    if (viewState.showDialog) {
        dateDialogState.show()
    } else {
        dateDialogState.hide()
    }
}

@Composable
fun EventList(
    viewState: CalendarState,
    effectHandler: (CalendarEffect) -> Unit
) {
    val events = viewState.eventInfoList
    if (events != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            repeat(events.size) { index ->
                EventCard(
                    eventInfo = events[index],
                    onCLick = {
                        effectHandler.invoke(CalendarEffect.OnEventClick(events[index].id))
                    }
                )
            }
        }
    }
}

@Composable
fun EventCard(
    eventInfo: EventInfo,
    onCLick: (Long) -> Unit
) {
    val minuteStart = eventInfo.dateStart.timeInMinutes()
    val minuteFinish = eventInfo.dateFinish.timeInMinutes()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = minuteStart.dp + dimensionResource(id = R.dimen.calendar_half_of_hour))
            .height((minuteFinish - minuteStart).dp)
            .padding(start = dimensionResource(id = R.dimen.step2))
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.step3)))
            .clickable { onCLick.invoke(eventInfo.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.step1))
        ) {
            Text(
                text = eventInfo.name,
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${eventInfo.dateStart.formatToTime()} - ${eventInfo.dateFinish.formatToTime()}",
                maxLines = 1,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun HoursList() {
    LazyColumn(
        modifier = Modifier
            .wrapContentWidth()
            .height(dimensionResource(id = R.dimen.calendar_height))
    ) {
        items(HOURS_IN_DAY) { index ->
            Box(
                modifier = Modifier.height(dimensionResource(id = R.dimen.calendar_hour))
            ) {
                val formattedIndex = if (index < COUNT_OF_DIGITS) {
                    "0$index:00"
                } else {
                    "$index:00"
                }
                Text(
                    text = formattedIndex,
                    modifier = Modifier.align(Alignment.CenterStart),
                    style = MaterialTheme.typography.bodySmall
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
            .padding(start = dimensionResource(id = R.dimen.step2))
            .height(dimensionResource(id = R.dimen.calendar_height))
    ) {
        items(HOURS_IN_DAY) { _ ->
            Box(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.calendar_hour))
            ) {
                Divider(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
