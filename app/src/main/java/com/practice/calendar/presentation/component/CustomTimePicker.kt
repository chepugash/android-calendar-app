package com.practice.calendar.presentation.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.practice.calendar.R
import com.practice.calendar.util.Click
import com.practice.calendar.util.formatToTime
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@Composable
fun CustomTimePicker(
    title: String,
    time: LocalTime,
    showDialog: Boolean,
    onOpen: Click,
    onClose: Click,
    onConfirm: (LocalTime) -> Unit
) {
    val timeDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier.wrapContentWidth()
    ) {
        TextButton(
            onClick = {
                onOpen()
            },
            modifier = Modifier.wrapContentWidth()
        ) {
            Text(
                text = time.formatToTime(),
                style = MaterialTheme.typography.titleLarge
            )
        }
        MaterialDialog(
            dialogState = timeDialogState,
            buttons = {
                positiveButton(
                    text = stringResource(R.string.date_picker_positive),
                    textStyle = MaterialTheme.typography.labelSmall
                )
                negativeButton(
                    text = stringResource(R.string.date_picker_negative),
                    textStyle = MaterialTheme.typography.labelSmall
                ) {
                    onClose()
                }
            },
            onCloseRequest = {
                onClose()
            },
            autoDismiss = false,
            backgroundColor = MaterialTheme.colorScheme.background
        ) {
            timepicker(
                initialTime = time,
                title = title,
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
                onConfirm(it)
            }
        }
    }
    if (showDialog) {
        timeDialogState.show()
    } else {
        timeDialogState.hide()
    }
}