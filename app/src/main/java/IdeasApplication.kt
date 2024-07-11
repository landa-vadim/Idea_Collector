import android.app.Application

class IdeasApplication: Application() {

    val mainDb: MainDb = MainDb.getDb(this)
    val repository: IdeasRepository = IdeasRepository(mainDb.getDao())
}