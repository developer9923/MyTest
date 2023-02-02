package com.app.berlinclock

import android.app.Application
import com.app.berlinclock.utils.BerlinClock
import com.app.berlinclock.viewmodel.BerlinClockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * @Author: Manoj Kumar
 * @Date: 01/02/23
 */
class BerlinClockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(appModule, viewModelModule))
        }
    }
}

val appModule = module {
   single { BerlinClock() }
}

val viewModelModule = module {
    viewModel { BerlinClockViewModel(get()) }
}