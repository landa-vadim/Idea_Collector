package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.Idea
import kotlinx.coroutines.flow.Flow

interface IdeasRepository {
    val ideasList: Flow<List<Idea>>
    suspend fun insertIdea(idea: Idea)
}