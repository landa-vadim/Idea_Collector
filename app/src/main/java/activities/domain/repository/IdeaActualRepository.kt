package activities.domain.repository

import data.Idea

interface IdeaActualRepository {
    fun saveToActual(idea: Idea)

    suspend fun loadData(): List<Idea>
}