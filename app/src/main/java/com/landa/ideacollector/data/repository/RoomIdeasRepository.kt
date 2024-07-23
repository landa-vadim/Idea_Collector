package com.landa.ideacollector.data.repository

import com.landa.ideacollector.data.db.IdeasDao
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

class RoomIdeasRepository(
    private val ideasDao: IdeasDao
) : IdeasRepository {

    override val ideasList: Flow<List<Idea>> = ideasDao.getAllItems()

    override suspend fun insertIdea(idea: Idea) {
        ideasDao.insertIdea(idea)
    }

    override suspend fun deleteIdea(idea: Idea) {
        ideasDao.deleteIdea(idea.id)
    }

    override suspend fun editIdea(idea: Idea) {
        ideasDao.editIdea(idea)
    }
}