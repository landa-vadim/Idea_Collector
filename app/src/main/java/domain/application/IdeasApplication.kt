package domain.application

import android.app.Application
import domain.di.appModule
import domain.di.dataModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
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



//    val mainDb by lazy { MainDb.getDb(this, applicationScope) }
//    val repository by lazy { IdeasRepository(mainDb.getDao()) }
//    val shrdPref by lazy { ShrdPref() }

}