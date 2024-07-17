package com.landa.ideacollector

import android.app.Application
import com.landa.ideacollector.di.appModule
import com.landa.ideacollector.di.dataModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class IdeasApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@IdeasApplication)
            modules(listOf(appModule, dataModule))
        }
    }

}