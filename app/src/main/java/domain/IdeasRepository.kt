package domain

import domain.dataBase.Dao
import data.dataClasses.Idea
import domain.utilityClasses.ShrdPref
import kotlinx.coroutines.flow.Flow

class IdeasRepository(private val dao: Dao, val shrdPref: ShrdPref) {
    val ideasList: Flow<List<Idea>> = dao.getAllItems()

    suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }

    val passCheckBoxState: Boolean = shrdPref.passCheckBoxGetValue()

    fun passCheckBoxSwitch(switch: Boolean) {
        shrdPref.passCheckBoxSetValue(switch)
    }

}