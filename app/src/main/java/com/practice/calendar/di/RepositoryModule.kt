package com.practice.calendar.di

import android.content.Context
import androidx.room.Room
import com.practice.calendar.data.local.AppDatabase
import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.remote.EventApi
import com.practice.calendar.data.remote.EventApiImpl
import com.practice.calendar.data.repository.EventRepositoryImpl
import com.practice.calendar.domain.repository.EventRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<EventRepository> {
        EventRepositoryImpl(api = get(), dao = get())
    }

    single<EventApi> {
        EventApiImpl(appContext = get())
    }

    single<AppDatabase> {
        provideDatabase(appContext = get())
    }

    single<EventDao> {
        provideEventDao(appDatabase = get())
    }
}

private fun provideDatabase(
    appContext: Context
): AppDatabase = Room.databaseBuilder(
    appContext,
    AppDatabase::class.java,
    AppDatabase.DB_NAME
).build()

private fun provideEventDao(
    appDatabase: AppDatabase
): EventDao = appDatabase.getEventDao()