package com.practice.calendar.ui.screen.detail

import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.practice.calendar.R
import com.practice.calendar.ui.navigation.DestinationScreen
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

@Composable
private fun DetailContent(
    eventId: Long,
    viewState: DetailState,
    effectHandler: (DetailEffect) -> Unit
) {
    LaunchedEffect(effectHandler) {
        effectHandler.invoke(DetailEffect.ShowEvent(eventId))
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val info = viewState.eventInfo
        if (info != null) {
            DetailToolbar(
                eventId = info.id,
                title = info.name,
                effectHandler = effectHandler
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                EventTitle(title = info.name)
                Spacer(modifier = Modifier.padding(8.dp))
                EventTime(
                    start = info.dateStart.formatToTime(),
                    finish = info.dateFinish.formatToTime()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                EventDate(
                    date = info.dateStart.formatToDate()
                )
                Spacer(modifier = Modifier.padding(8.dp))
                EventDescription(desc = info.description)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailToolbar(
    eventId: Long,
    title: String,
    effectHandler: (DetailEffect) -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 24.sp
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
    Box(
        modifier = Modifier.wrapContentSize()
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
            contentDescription = stringResource(R.string.watches_icon_in_details),
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
        )
        Text(
            text = start,
            fontSize = 24.sp,
        )
        Text(
            text = stringResource(R.string.time_divider),
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
            contentDescription = stringResource(R.string.calendar_icon_in_details),
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
            fontSize = 18.sp
        )
    }
}
