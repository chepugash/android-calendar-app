package com.practice.calendar.ui.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import com.practice.calendar.util.formatToDate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

class CalendarViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val updateEventsFromRemoteUseCase: UpdateEventsFromRemoteUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CalendarState>(CalendarState())
    val state: StateFlow<CalendarState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<CalendarAction?>()
    val action: SharedFlow<CalendarAction?>
        get() = _action.asSharedFlow()

    fun effect(calendarEffect: CalendarEffect) {
        when(calendarEffect) {
            CalendarEffect.OnDateClick -> onDateClick()
            is CalendarEffect.OnConfirmDialog -> onConfirmDialog(calendarEffect)
            CalendarEffect.OnCloseDialog -> onCloseDialog()
        }
    }

    init {
        getEvents(LocalDate.now())
    }

    private fun getEvents(date: LocalDate) {
        viewModelScope.launch {
            updateEventsFromRemoteUseCase()
            getEventsUseCase(date)
                .collect { list ->
                    _state.emit(
                        _state.value.copy(
                            eventInfoList = list?.toPersistentList()
                        )
                    )
                }
        }
    }

    private fun onConfirmDialog(effect: CalendarEffect.OnConfirmDialog) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDialog = false,
                    date = effect.date
                )
            )
            getEvents(state.value.date)
            Log.e("AAAAA", state.value.showDialog.toString())
        }
    }

    private fun onCloseDialog() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDialog = false,
                )
            )
            Log.e("AAAAA", state.value.showDialog.toString())
        }
    }

    private fun onDateClick() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDialog = true
                )
            )
            Log.e("AAAAA", state.value.date.formatToDate())
        }
    }
}
