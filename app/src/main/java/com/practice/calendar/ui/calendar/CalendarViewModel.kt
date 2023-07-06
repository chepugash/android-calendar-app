package com.practice.calendar.ui.calendar

import androidx.lifecycle.ViewModel
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

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

    }
}