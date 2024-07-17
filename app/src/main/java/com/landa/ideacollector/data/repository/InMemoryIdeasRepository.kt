package com.landa.ideacollector.data.repository

import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class InMemoryIdeasRepository : IdeasRepository {

    val list = mutableListOf<Idea>()

    override val ideasList: Flow<List<Idea>> = flowOf(list)

    override suspend fun insertIdea(idea: Idea) {
        list.add(idea)
    }
}