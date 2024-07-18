package com.landa.ideacollector.data.repository

import com.landa.ideacollector.data.db.IdeasDao
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

class RoomIdeasRepository(
    private val dao: IdeasDao
) : IdeasRepository {

    override val ideasList: Flow<List<Idea>> = dao.getAllItems()

    override suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }
}