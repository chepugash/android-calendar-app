package com.practice.calendar.di

import android.content.Context
import androidx.room.Room
import com.practice.calendar.data.local.AppDatabase
import com.practice.calendar.data.local.dao.EventDao
import com.practice.calendar.data.remote.EventApi
import com.practice.calendar.data.remote.EventApiImpl
import com.practice.calendar.data.repository.EventRepositoryImpl
import com.practice.calendar.domain.repository.EventRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        eventRepositoryImpl: EventRepositoryImpl
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindEventApi(
        eventApiImpl: EventApiImpl
    ): EventApi

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideEventDao(
        appDatabase: AppDatabase
    ): EventDao = appDatabase.getEventDao()
}