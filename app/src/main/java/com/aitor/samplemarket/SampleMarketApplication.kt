package com.aitor.samplemarket

import android.app.Application
import com.aitor.samplemarket.base.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SampleMarketApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Dependency injection through Koin
        startKoin {
            androidContext(this@SampleMarketApplication)
            modules(listOf(apiModule, productModule, discountModule, cartModule, viewModelModule))

            Timber.plant(Timber.DebugTree())
        }
    }
}