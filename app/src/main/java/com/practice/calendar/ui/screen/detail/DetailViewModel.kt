package com.practice.calendar.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.DeleteEventUseCase
import com.practice.calendar.domain.usecase.GetEventUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getEventUseCase: GetEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state: StateFlow<DetailState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<DetailAction?>()
    val action: SharedFlow<DetailAction?>
        get() = _action.asSharedFlow()

    fun effect(detailEffect: DetailEffect) {
        when(detailEffect) {
            is DetailEffect.ShowEvent -> getEvent(detailEffect.eventId)
            is DetailEffect.OnDeleteClick -> onDeleteClick(detailEffect.eventId)
        }
    }

    private fun getEvent(eventId: Long) {
        viewModelScope.launch {
            getEventUseCase(eventId).collect {
                _state.emit(
                    _state.value.copy(
                        eventInfo = it
                    )
                )
            }
        }
    }

    private fun onDeleteClick(eventId: Long) {
        viewModelScope.launch {
            deleteEventUseCase(eventId)
            _action.emit(
                DetailAction.NavigateToCalendar
            )
        }
    }
}