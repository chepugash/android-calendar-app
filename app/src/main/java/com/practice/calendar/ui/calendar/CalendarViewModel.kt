package com.practice.calendar.ui.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val updateEventsFromRemoteUseCase: UpdateEventsFromRemoteUseCase
): ViewModel() {

    private val _state = MutableStateFlow<CalendarState>(CalendarState())
    val state: StateFlow<CalendarState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<CalendarAction?>()
    val action: SharedFlow<CalendarAction?>
        get() = _action.asSharedFlow()

    fun effect(calendarEffect: CalendarEffect) {

    }
}