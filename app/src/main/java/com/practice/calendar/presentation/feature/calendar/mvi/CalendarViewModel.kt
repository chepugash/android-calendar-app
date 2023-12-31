package com.practice.calendar.presentation.feature.calendar.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import com.practice.calendar.presentation.entity.PresentationMapper
import com.practice.calendar.util.groupByTime
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val getEventsUseCase: GetEventsUseCase,
    private val updateEventsFromRemoteUseCase: UpdateEventsFromRemoteUseCase,
    private val mapper: PresentationMapper
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
            CalendarEffect.OnAddEventClick -> onAddEventClick()
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                updateEventsFromRemoteUseCase()

                val mappedDate = mapper.localDateToTimestamp(state.value.date)
                val mappedFlow = mapper.eventInfoListFlowToEventPresentationEntityListFlow(
                    getEventsUseCase(mappedDate)
                )

                mappedFlow.groupByTime().collect { list ->
                    val newState = _state.value.copy(
                        eventPresentationEntityList = list?.map { sublist ->
                            sublist.toPersistentList()
                        }?.toPersistentList()
                    )
                    _state.emit(newState)
                }

            } catch (e: Throwable) {
                _action.emit(CalendarAction.ShowToast(e.message.toString()))
            }
        }
    }

    private fun onConfirmDialog(effect: CalendarEffect.OnConfirmDialog) {
        viewModelScope.launch {
            val newState = _state.value.copy(showDialog = false, date = effect.date)
            _state.emit(newState)
            getEvents()
        }
    }

    private fun onCloseDialog() {
        viewModelScope.launch {
            val newState = _state.value.copy(showDialog = false)
            _state.emit(newState)
        }
    }

    private fun onDateClick() {
        viewModelScope.launch {
            val newState = _state.value.copy(showDialog = true)
            _state.emit(newState)
        }
    }

    private fun onEventClick(eventId: Long) {
        viewModelScope.launch {
            _action.emit(CalendarAction.NavigateDetail(eventId = eventId))
        }
    }

    private fun onAddEventClick() {
        viewModelScope.launch {
            _action.emit(
                CalendarAction.NavigateAddEvent
            )
        }
    }
}
