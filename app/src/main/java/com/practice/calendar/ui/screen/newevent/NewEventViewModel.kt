package com.practice.calendar.ui.screen.newevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.usecase.CreateEventUseCase
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
                onDescriptionChanged(newEventEffect.desc)
            }

            is NewEventEffect.OnNameChanged -> {
                onNameChanged(newEventEffect.name)
            }
        }
    }

    private fun onTimeStartClick() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeStartDialog = true
                )
            )
        }
    }

    private fun onTimeFinishClick() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeFinishDialog = true
                )
            )
        }
    }

    private fun onNameChanged(name: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    name = name
                )
            )
        }
    }

    private fun onDescriptionChanged(desc: String) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    description = desc
                )
            )
        }
    }

    private fun onDateClick() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDateDialog = true
                )
            )
        }
    }

    private fun onConfirmTimeStartDialog(timeStart: LocalTime) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeStartDialog = false,
                    timeStart = timeStart
                )
            )
        }
    }

    private fun onConfirmTimeFinishDialog(timeFinish: LocalTime) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeFinishDialog = false,
                    timeFinish = timeFinish
                )
            )
        }
    }

    private fun onConfirmDateDialog(date: LocalDate) {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDateDialog = false,
                    date = date
                )
            )
        }
    }

    private fun onCloseTimeStartDialog() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeStartDialog = false
                )
            )
        }
    }

    private fun onCloseTimeFinishDialog() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showTimeFinishDialog = false
                )
            )
        }
    }

    private fun onCloseDateDialog() {
        viewModelScope.launch {
            _state.emit(
                _state.value.copy(
                    showDateDialog = false
                )
            )
        }
    }

    private fun onConfirmClick() {
        viewModelScope.launch {
            val createdId = createEventUseCase(EventInfo(
                id = 0,
                name = state.value.name,
                dateStart = state.value.date.atTime(state.value.timeStart),
                dateFinish = state.value.date.atTime(state.value.timeFinish),
                description = state.value.description
            ))
            _action.emit(
                NewEventAction.NavigateToDetail(createdId)
            )
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            _action.emit(
                NewEventAction.NavigateBack
            )
        }
    }
}