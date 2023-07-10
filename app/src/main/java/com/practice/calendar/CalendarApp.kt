package com.practice.calendar

import android.app.Application
import com.practice.calendar.di.appModule
import com.practice.calendar.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CalendarApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CalendarApp)
            modules(appModule, repositoryModule)
        }
    }
}
