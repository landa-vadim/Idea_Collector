package com.landa.ideacollector.data.repository

import com.landa.ideacollector.data.db.Dao
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

class RoomIdeasRepository(
    private val dao: Dao
) : IdeasRepository {

    override val ideasList: Flow<List<Idea>> = dao.getAllItems()

    override suspend fun insertIdea(idea: Idea) {
        dao.insertIdea(idea)
    }
}