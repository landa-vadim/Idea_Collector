package activities.domain.repository

import data.Idea

interface IdeaHistoryRepository {
    fun saveToHistory(idea: Idea)
}