package com.practice.calendar.ui.screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.practice.calendar.R
import com.practice.calendar.ui.screen.calendar.CalendarEffect
import com.practice.calendar.util.Click
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun CustomDatePickerDialog(
    date: LocalDate,
    dialogState: MaterialDialogState,
    onClose: Click,
    onConfirm: (LocalDate) -> Unit
) {
    MaterialDialog(
        dialogState = dialogState,
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
            onConfirm(it)
        }
    }
}