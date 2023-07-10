package com.practice.calendar.presentation.feature.detail.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practice.calendar.domain.usecase.DeleteEventUseCase
import com.practice.calendar.domain.usecase.GetEventUseCase
import com.practice.calendar.presentation.entity.PresentationMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getEventUseCase: GetEventUseCase,
    private val deleteEventUseCase: DeleteEventUseCase,
    private val mapper: PresentationMapper
) : ViewModel() {

    private val _state = MutableStateFlow<DetailState>(DetailState())
    val state: StateFlow<DetailState>
        get() = _state.asStateFlow()

    private val _action = MutableSharedFlow<DetailAction?>()
    val action: SharedFlow<DetailAction?>
        get() = _action.asSharedFlow()

    fun effect(detailEffect: DetailEffect) {
        when (detailEffect) {
            is DetailEffect.ShowEvent -> getEvent(detailEffect.eventId)
            is DetailEffect.OnDeleteClick -> onDeleteClick(detailEffect.eventId)
            DetailEffect.OnBackClick -> onBackClick()
        }
    }

    private fun getEvent(eventId: Long) {
        viewModelScope.launch {
            try {
                getEventUseCase(eventId).collect {
                    val mappedEvent = mapper.eventInfoToEventPresentationEntity(it)
                    val newState = _state.value.copy(eventEntity = mappedEvent)
                    _state.emit(newState)
                }
            } catch (e: Throwable) {
                _action.emit(
                    DetailAction.ShowToast(e.message.toString())
                )
            }
        }
    }

    private fun onDeleteClick(eventId: Long) {
        viewModelScope.launch {
            try {
                deleteEventUseCase(eventId)
                _action.emit(DetailAction.NavigateBack)
            } catch (e: Throwable) {
                _action.emit(DetailAction.ShowToast(e.message.toString()))
            }
        }
    }

    private fun onBackClick() {
        viewModelScope.launch {
            _action.emit(DetailAction.NavigateBack)
        }
    }
}
