package com.practice.calendar.presentation.feature.detail.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.calendar.R
import com.practice.calendar.presentation.feature.detail.mvi.DetailAction
import com.practice.calendar.presentation.feature.detail.mvi.DetailEffect
import com.practice.calendar.presentation.feature.detail.mvi.DetailState
import com.practice.calendar.presentation.feature.detail.mvi.DetailViewModel
import com.practice.calendar.presentation.navigation.DestinationScreen
import com.practice.calendar.util.formatToDate
import com.practice.calendar.util.formatToTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    eventId: Long,
    viewModel: DetailViewModel = koinViewModel(),
    navController: NavController
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    DetailContent(
        eventId = eventId,
        viewState = state.value,
        effectHandler = viewModel::effect
    )

    DetailScreenActions(
        navController = navController,
        viewAction = action
    )

    BackHandler {
        viewModel::effect.invoke(DetailEffect.OnBackClick)
    }
}

@Composable
private fun DetailContent(
    eventId: Long,
    viewState: DetailState,
    effectHandler: (DetailEffect) -> Unit
) {
    LaunchedEffect(effectHandler) {
        effectHandler.invoke(DetailEffect.ShowEvent(eventId))
    }
    val info = viewState.eventEntity ?: return
    Scaffold(
        topBar = {
            DetailToolbar(
                eventId = info.id,
                effectHandler = effectHandler
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.step4),
                    end = dimensionResource(id = R.dimen.step4),
                    bottom = dimensionResource(id = R.dimen.step4),
                    top = it.calculateTopPadding()
                )
        ) {
            EventTitle(title = info.name)
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventTime(
                start = info.dateStart.formatToTime(),
                finish = info.dateFinish.formatToTime()
            )
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventDate(date = info.dateStart.formatToDate())
            Divider(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.step4)))

            EventDescription(desc = info.description)
        }
    }
}

@Composable
private fun DetailScreenActions(
    navController: NavController,
    viewAction: DetailAction?
) {
    val context = LocalContext.current
    LaunchedEffect(viewAction) {
        when (viewAction) {
            null -> Unit
            DetailAction.NavigateBack -> {
                navController.popBackStack(
                    route = DestinationScreen.CalendarScreen.route,
                    inclusive = false
                )
            }

            is DetailAction.ShowToast -> {
                Toast.makeText(context, viewAction.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(
    eventId: Long,
    effectHandler: (DetailEffect) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.event),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    effectHandler.invoke(DetailEffect.OnBackClick)
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_back),
                    contentDescription = stringResource(R.string.detail_toolbar_back_icon),
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    effectHandler.invoke(DetailEffect.OnDeleteClick(eventId))
                }
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_delete),
                    contentDescription = stringResource(R.string.detail_toolbar_delete_icon),
                )
            }
        }
    )
}

@Composable
fun EventTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_title),
            contentDescription = stringResource(R.string.title_icon_in_details),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.step2))
                .align(Alignment.CenterVertically)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
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
            contentDescription = stringResource(R.string.watches_icon_in_details),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.step2))
                .align(Alignment.CenterVertically)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = start,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(R.string.time_divider),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.step2))
            )
            Text(
                text = finish,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun EventDate(date: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painterResource(id = R.drawable.ic_calendar),
            contentDescription = stringResource(R.string.calendar_icon_in_details),
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.step2))
                .align(Alignment.CenterVertically)
        )
        Text(
            text = date,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EventDescription(desc: String) {
    Box {
        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
