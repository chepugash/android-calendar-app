package com.practice.calendar.ui.screen.newevent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.usecase.GetEventUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class NewEventViewModel(
    private val getEventUseCase: GetEventUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<NewEventState>(NewEventState())
    val state: StateFlow<NewEventState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<NewEventAction?>()
    val action: SharedFlow<NewEventAction?>
        get() = _action.asSharedFlow()

    fun effect(newEventEffect: NewEventEffect) {
        when(newEventEffect) {
            is NewEventEffect.ShowEvent -> getEvent(newEventEffect.eventId)
            NewEventEffect.OnCloseDateDialog -> onCloseDateDialog()
            NewEventEffect.OnCloseTimeFinishDialog -> onCloseTimeFinishDialog()
            NewEventEffect.OnCloseTimeStartDialog -> onCloseTimeStartDialog()
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
            NewEventEffect.OnTimeFinishClick -> onTimeFinishClick()
            NewEventEffect.OnTimeStartClick -> onTimeStartClick()
        }
    }

    private fun onTimeStartClick() {}

    private fun onTimeFinishClick() {}

    private fun onNameChanged(name: String) {}

    private fun onDescriptionChanged(desc: String) {}

    private fun onDateClick() {}

    private fun onConfirmTimeStartDialog(timeStart: LocalTime) {}

    private fun onConfirmTimeFinishDialog(timeFinish: LocalTime) {}

    private fun onConfirmDateDialog(date: LocalDate) {}

    private fun onCloseTimeStartDialog() {}

    private fun onCloseTimeFinishDialog() {}

    private fun onCloseDateDialog() {}

    // get event from data or create basic if null
    private fun getEvent(eventId: Long) {
        viewModelScope.launch {
            getEventUseCase(eventId).collect {
                if (it == null) {
                    _state.emit(
                        _state.value.copy(
                            eventInfo = EventInfo(
                                id = 0,
                                name = "",
                                dateStart = LocalDateTime.now(),
                                dateFinish = LocalDateTime.now().plusHours(1),
                                description = ""
                            )
                        )
                    )
                } else {
                    _state.emit(
                        _state.value.copy(
                            eventInfo = it
                        )
                    )
                }
            }
        }
    }
}