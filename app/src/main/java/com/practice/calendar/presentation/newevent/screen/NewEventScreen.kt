package com.practice.calendar.presentation.newevent.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.calendar.R
import com.practice.calendar.presentation.navigation.DestinationScreen
import com.practice.calendar.presentation.component.CustomDatePickerDialog
import com.practice.calendar.presentation.component.CustomTimePicker
import com.practice.calendar.presentation.newevent.mvi.NewEventAction
import com.practice.calendar.presentation.newevent.mvi.NewEventEffect
import com.practice.calendar.presentation.newevent.mvi.NewEventState
import com.practice.calendar.presentation.newevent.mvi.NewEventViewModel
import com.practice.calendar.util.formatToDate
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

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
                modifier = Modifier.fillMaxSize().weight(1f)
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
                        style = MaterialTheme.typography.titleMedium
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
                style = MaterialTheme.typography.bodyLarge
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
            style = MaterialTheme.typography.bodyMedium,
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
            textStyle = MaterialTheme.typography.bodyMedium,
            singleLine = true,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_edit),
                    contentDescription = stringResource(R.string.title_text_edit_icon),
                )
            },
            trailingIcon = {
                Text(
                    text = "${name.length}/$maxSize",
                    style = MaterialTheme.typography.bodySmall
                )
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
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomTimePicker(
                title = stringResource(id = R.string.start_time_picker_title),
                time = viewState.timeStart,
                showDialog = viewState.showTimeStartDialog,
                onOpen = {
                    effectHandler.invoke(NewEventEffect.OnTimeStartClick)
                },
                onClose = {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeStartDialog)
                },
                onConfirm = {
                    effectHandler.invoke(NewEventEffect.OnConfirmTimeStartDialog(it))
                }
            )
            Text(
                text = stringResource(R.string.time_divider),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            CustomTimePicker(
                title = stringResource(id = R.string.finish_time_picker_title),
                time = viewState.timeFinish,
                showDialog = viewState.showTimeFinishDialog,
                onOpen = {
                    effectHandler.invoke(NewEventEffect.OnTimeFinishClick)
                },
                onClose = {
                    effectHandler.invoke(NewEventEffect.OnCloseTimeFinishDialog)
                },
                onConfirm = {
                    effectHandler.invoke(NewEventEffect.OnConfirmTimeFinishDialog(it))
                }
            )
        }
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
                style = MaterialTheme.typography.titleLarge
            )
        }
        CustomDatePickerDialog(
            date = date,
            dialogState = dateDialogState,
            onClose = {
                effectHandler.invoke(NewEventEffect.OnCloseDateDialog)
            },
            onConfirm = {
                effectHandler.invoke(NewEventEffect.OnConfirmDateDialog(it))
            }
        )
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
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.step2))
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = desc,
            onValueChange = {
                effectHandler.invoke(NewEventEffect.OnDescriptionChanged(it))
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_description),
                    contentDescription = stringResource(R.string.description_edit_icon_in_new_event),
                )
            },
            minLines = 4,
            maxLines = 14,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
