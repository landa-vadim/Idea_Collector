package activities.domain.interactor

import data.Priority

interface IdeaInteractor {
    fun handleIdea(title: String, priority: Priority)
}