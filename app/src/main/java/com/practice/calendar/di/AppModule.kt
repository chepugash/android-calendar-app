package com.practice.calendar.di

import com.practice.calendar.domain.usecase.GetEventUseCase
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import com.practice.calendar.ui.calendar.CalendarViewModel
import com.practice.calendar.ui.detail.DetailViewModel
import com.practice.calendar.ui.detail.event
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<GetEventsUseCase> {
        GetEventsUseCase(eventRepository = get())
    }

    factory<UpdateEventsFromRemoteUseCase> {
        UpdateEventsFromRemoteUseCase(eventRepository = get())
    }

    factory<GetEventUseCase> {
        GetEventUseCase(eventRepository = get())
    }

    viewModel<CalendarViewModel> {
        CalendarViewModel(
            getEventsUseCase = get(),
            updateEventsFromRemoteUseCase = get()
        )
    }

    viewModel<DetailViewModel> {
        DetailViewModel(
            getEventUseCase = get()
        )
    }
}
