package activities.domain.repository

import data.Idea

interface IdeaActualRepository {
    fun saveToActual(idea: Idea)

    fun loadData(): List<Idea>
}