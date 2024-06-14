package activities.data

import activities.domain.repository.IdeaActualRepository
import data.Idea

class DbIdeaActualRepository : IdeaActualRepository {
    override fun saveToActual(idea: Idea) {
        // real save to DB
    }
}