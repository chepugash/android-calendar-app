package com.practice.calendar.presentation.newevent.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.usecase.CreateEventUseCase
import com.practice.calendar.util.EmptyNameException
import com.practice.calendar.util.TimeFinishLessThanStartException
import com.practice.calendar.util.TimePeriodLessThanHalfHour
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class NewEventViewModel(
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewEventState>(NewEventState())
    val state: StateFlow<NewEventState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<NewEventAction?>()
    val action: SharedFlow<NewEventAction?>
        get() = _action.asSharedFlow()

    fun effect(newEventEffect: NewEventEffect) {
        when (newEventEffect) {
            NewEventEffect.OnCloseDateDialog -> onCloseDateDialog()
            NewEventEffect.OnCloseTimeFinishDialog -> onCloseTimeFinishDialog()
            NewEventEffect.OnCloseTimeStartDialog -> onCloseTimeStartDialog()
            NewEventEffect.OnTimeFinishClick -> onTimeFinishClick()
            NewEventEffect.OnTimeStartClick -> onTimeStartClick()

            NewEventEffect.OnConfirmClick -> onConfirmClick()
            NewEventEffect.OnBackClick -> onBackClick()

            is NewEventEffect.OnConfirmDateDialog -> {
                onConfirmDateDialog(newEventEffect.date)
            }

            is NewEventEffect.OnConfirmTimeFinishDialog -> {
                onConfirmTimeFinishDialog(newEventEffect.timeFinish)
            }

            is NewEventEffect.OnConfirmTimeStartDialog -> {
                onConfirmTimeStartDialog(newEventEffect.timeStart)
            }

            NewEventEffect.OnDateClick -> onDateClick()
            is NewEventEffect.OnDescriptionChanged -> {
                onDescriptionChanged(newEventEffect.description)
            }

            is NewEventEffect.OnNameChanged -> {
                onNameChanged(newEventEffect.name)
            }
        }
    }

    private fun onTimeStartClick() {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeStartDialog = true)
            _state.emit(newState)
        }
    }

    private fun onTimeFinishClick() {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeFinishDialog = true)
            _state.emit(newState)
        }
    }

    private fun onNameChanged(name: String) {
        viewModelScope.launch {
            val newState = _state.value.copy(name = name)
            _state.emit(newState)
        }
    }

    private fun onDescriptionChanged(description: String) {
        viewModelScope.launch {
            val newState = _state.value.copy(description = description)
            _state.emit(newState)
        }
    }

    private fun onDateClick() {
        viewModelScope.launch {
            val newState = _state.value.copy(showDateDialog = true)
            _state.emit(newState)
        }
    }

    private fun onConfirmTimeStartDialog(timeStart: LocalTime) {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeStartDialog = false, timeStart = timeStart)
            _state.emit(newState)
        }
    }

    private fun onConfirmTimeFinishDialog(timeFinish: LocalTime) {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeFinishDialog = false, timeFinish = timeFinish)
            _state.emit(newState)
        }
    }

    private fun onConfirmDateDialog(date: LocalDate) {
        viewModelScope.launch {
            val newState = _state.value.copy(showDateDialog = false, date = date)
            _state.emit(newState)
        }
    }

    private fun onCloseTimeStartDialog() {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeStartDialog = false)
            _state.emit(newState)
        }
    }

    private fun onCloseTimeFinishDialog() {
        viewModelScope.launch {
            val newState = _state.value.copy(showTimeFinishDialog = false)
            _state.emit(newState)
        }
    }

    private fun onCloseDateDialog() {
        viewModelScope.launch {
            val newState = _state.value.copy(showDateDialog = false)
            _state.emit(newState)
        }
    }

    private fun onConfirmClick() {
        viewModelScope.launch {
            try {
                validate(state.value.name, state.value.timeStart, state.value.timeFinish)
                val createdId = createEventUseCase(EventInfo(
                    id = 0,
                    name = state.value.name,
                    dateStart = state.value.date.atTime(state.value.timeStart),
                    dateFinish = state.value.date.atTime(state.value.timeFinish),
                    description = state.value.description
                ))
                _action.emit(NewEventAction.NavigateToDetail(createdId))
            } catch (e: Throwable) {
                _action.emit(NewEventAction.ShowToast(e.message.toString()))
            }
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            _action.emit(NewEventAction.NavigateBack)
        }
    }

    private fun validate(name: String, timeStart: LocalTime, timeFinish: LocalTime) {
        if (name.isBlank()) throw EmptyNameException("Name can not be empty")
        if (timeFinish <= timeStart) {
            throw TimeFinishLessThanStartException("Finish time must be more than start time")
        }
        if (timeStart.hour == timeFinish.hour
            && timeFinish.minute - timeStart.minute < MIN_MINUTES) {
            throw TimePeriodLessThanHalfHour("Time period can not be less than $MIN_MINUTES minutes")
        }
    }

    companion object {
        private const val MIN_MINUTES = 30
    }
}