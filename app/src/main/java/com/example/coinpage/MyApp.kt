package com.example.coinpage

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.example.coinpage.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp:Application() {

    @ExperimentalPagingApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            androidLogger()
            modules(appModule)
        }
    }
}