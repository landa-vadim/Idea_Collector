import kotlinx.coroutines.flow.Flow

class IdeasRepository(private val dao: Dao) {
    val mainDb: MainDb = IdeasApplication().mainDb
    val ideasList: Flow<List<Idea>> = dao.getAllItems()

    suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }
}