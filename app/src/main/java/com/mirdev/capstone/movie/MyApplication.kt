package com.mirdev.capstone.movie

import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.mirdev.capstone.core.di.databaseModule
import com.mirdev.capstone.core.di.networkModule
import com.mirdev.capstone.core.di.repositoryModule
import com.mirdev.capstone.movie.di.useCaseModule
import com.mirdev.capstone.movie.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : SplitCompatApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}