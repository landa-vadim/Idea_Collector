package domain

import domain.dataBase.Dao
import domain.dataClasses.Idea
import kotlinx.coroutines.flow.Flow

class IdeasRepository(private val dao: Dao) {
    val ideasList: Flow<List<Idea>> = dao.getAllItems()

    suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }
}