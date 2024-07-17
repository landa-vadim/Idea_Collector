package domain

import domain.dataBase.Dao
import data.dataClasses.Idea
import data.dataClasses.SortTypeEnum
import data.dataClasses.ThemeEnum
import domain.utilityClasses.DataStoreManager
import domain.utilityClasses.ShrdPref
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow

class IdeasRepository(
    private val dao: Dao,
    private val shrdPref: ShrdPref,
    private val dataStoreManager: DataStoreManager
) {
    val ideasList: Flow<List<Idea>> = dao.getAllItems()

    suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }

    val passCheckBoxState: Boolean = shrdPref.passCheckBoxGetValue()

    fun passCheckBoxSwitch(switch: Boolean) {
        shrdPref.passCheckBoxSetValue(switch)
    }

    suspend fun setSortedType(sortedType: Int) {
        dataStoreManager.setSortedTypeValue(sortedType)
    }

    suspend fun setTheme(theme: Int) {
        dataStoreManager.setThemeValue(theme)
    }

    val sortedType = dataStoreManager.getSortTypeValue()
    val theme = dataStoreManager.getThemeValue()

}