package com.practice.calendar.ui.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val updateEventsFromRemoteUseCase: UpdateEventsFromRemoteUseCase
): ViewModel() {

    var state by mutableStateOf(CalendarState())
        private set

    fun loadEventsByDate(date: LocalDate) {
        viewModelScope.launch {
            try {
                getEventsUseCase(date).let {
                    it.collect { list ->
                        state = state.copy(
                            eventInfoList = list,
                            error = null
                        )
                    }
                }
                updateEventsFromRemoteUseCase()
            } catch(error: Throwable) {
                state = state.copy(
                    eventInfoList = null,
                    error = error.message
                )
            }
        }
    }
}