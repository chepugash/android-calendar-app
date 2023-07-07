package com.practice.calendar.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
        when (calendarEffect) {
            CalendarEffect.OnDateClick -> onDateClick()
            is CalendarEffect.OnConfirmDialog -> onConfirmDialog(calendarEffect)
            CalendarEffect.OnCloseDialog -> onCloseDialog()
            is CalendarEffect.OnEventClick -> onEventClick(calendarEffect.eventId)
        }
    }

    init {
        getEvents(LocalDate.now())
    }

    private fun getEvents(date: LocalDate) {
        viewModelScope.launch {
            updateEventsFromRemoteUseCase()
            getEventsUseCase(date).collect { list ->
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
        }
    }

    private fun onCloseDialog() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDialog = false,
                )
            )
        }
    }

    private fun onDateClick() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDialog = true
                )
            )
        }
    }

    private fun onEventClick(eventId: Long) {
        viewModelScope.launch {
            _action.emit(
                CalendarAction.NavigateDetail(eventId = eventId)
            )
        }
    }
}
