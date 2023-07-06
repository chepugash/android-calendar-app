package com.practice.calendar.di

import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import com.practice.calendar.ui.calendar.CalendarViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<GetEventsUseCase> {
        GetEventsUseCase(eventRepository = get())
    }

    factory<UpdateEventsFromRemoteUseCase> {
        UpdateEventsFromRemoteUseCase(eventRepository = get())
    }

    viewModel<CalendarViewModel> {
        CalendarViewModel(
            getEventsUseCase = get(),
            updateEventsFromRemoteUseCase = get()
        )
    }

}
