package domain

import android.app.Application
import domain.dataBase.MainDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class IdeasApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val mainDb by lazy { MainDb.getDb(this, applicationScope) }
    val repository by lazy { IdeasRepository(mainDb.getDao()) }

}