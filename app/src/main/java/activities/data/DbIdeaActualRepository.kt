package activities.data

import activities.domain.repository.IdeaActualRepository
import adaptors.IdeaAdapter
import data.Idea
import data.SortTypeEnum
import data.dataBase.MainDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DbIdeaActualRepository : IdeaActualRepository {
    val db = MainDb.getDb()
    val adapter = IdeaAdapter()
    override fun saveToActual(idea: Idea) {
        CoroutineScope(Dispatchers.Main).launch {
            db.getDao().insertIdea(idea)
            val oldIdeaList = db.getDao().getAllItems()
            adapter.setData(oldIdeaList, SortTypeEnum.DATE)
        }
    }

    override suspend fun loadData(): List<Idea> {
        CoroutineScope(Dispatchers.IO).launch {
            val oldIdeasList = db.getDao().getAllItems()
            withContext(Dispatchers.Main).launch {
                val newIdeaList = adapter.setData(oldIdeasList, SortTypeEnum.DATE)
            }
        }
        return newIdeaList
    }

}