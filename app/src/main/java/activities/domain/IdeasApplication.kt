package activities.domain

import android.app.Application
import data.dataBase.MainDb

class IdeasApplication (
    val mainDb = MainDb.getDb()
): Application() {

}