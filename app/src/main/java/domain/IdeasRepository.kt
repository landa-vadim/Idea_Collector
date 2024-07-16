package domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import domain.dataBase.Dao
import data.dataClasses.Idea
import domain.utilityClasses.ShrdPref
import kotlinx.coroutines.flow.Flow

class IdeasRepository(private val dao: Dao, val shrdPref: ShrdPref, val dataStore: DataStore<Preferences>) {
    val ideasList: Flow<List<Idea>> = dao.getAllItems()

    suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }

    val passCheckBoxState: Boolean = shrdPref.passCheckBoxGetValue()

    fun passCheckBoxSwitch(switch: Boolean) {
        shrdPref.passCheckBoxSetValue(switch)
    }

}