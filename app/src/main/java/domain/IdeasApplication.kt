package domain

import android.app.Application
import domain.dataBase.MainDb

class IdeasApplication: Application() {

    val mainDb: MainDb = MainDb.getDb(this)
    val repository: IdeasRepository = IdeasRepository(mainDb.getDao())
}