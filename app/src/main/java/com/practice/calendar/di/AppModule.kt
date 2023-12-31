package com.practice.calendar.di

import com.practice.calendar.domain.usecase.CreateEventUseCase
import com.practice.calendar.domain.usecase.DeleteEventUseCase
import com.practice.calendar.domain.usecase.GetEventUseCase
import com.practice.calendar.domain.usecase.GetEventsUseCase
import com.practice.calendar.domain.usecase.UpdateEventsFromRemoteUseCase
import com.practice.calendar.domain.usecase.impl.CreateEventUseCaseImpl
import com.practice.calendar.domain.usecase.impl.DeleteEventUseCaseImpl
import com.practice.calendar.domain.usecase.impl.GetEventUseCaseImpl
import com.practice.calendar.domain.usecase.impl.GetEventsUseCaseImpl
import com.practice.calendar.domain.usecase.impl.UpdateEventsFromRemoteUseCaseImpl
import com.practice.calendar.presentation.entity.PresentationMapper
import com.practice.calendar.presentation.feature.calendar.mvi.CalendarViewModel
import com.practice.calendar.presentation.feature.detail.mvi.DetailViewModel
import com.practice.calendar.presentation.feature.newevent.mvi.NewEventViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<GetEventsUseCase> {
        GetEventsUseCaseImpl(eventRepository = get())
    }

    factory<UpdateEventsFromRemoteUseCase> {
        UpdateEventsFromRemoteUseCaseImpl(eventRepository = get())
    }

    factory<GetEventUseCase> {
        GetEventUseCaseImpl(eventRepository = get())
    }

    factory<CreateEventUseCase> {
        CreateEventUseCaseImpl(eventRepository = get())
    }

    factory<DeleteEventUseCase> {
        DeleteEventUseCaseImpl(eventRepository = get())
    }

    factory<PresentationMapper> {
        PresentationMapper()
    }

    viewModel<CalendarViewModel> {
        CalendarViewModel(
            getEventsUseCase = get(),
            updateEventsFromRemoteUseCase = get(),
            mapper = get()
        )
    }

    viewModel<DetailViewModel> {
        DetailViewModel(
            getEventUseCase = get(),
            deleteEventUseCase = get(),
            mapper = get()
        )
    }

    viewModel<NewEventViewModel> {
        NewEventViewModel(
            createEventUseCase = get(),
            mapper = get()
        )
    }
}
