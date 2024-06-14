package activities.domain

import activities.domain.interactor.IdeaInteractor
import activities.domain.repository.IdeaActualRepository
import activities.domain.repository.IdeaHistoryRepository
import data.Idea
import data.Priority
import java.util.Date

class RealIdeaInteractor(
    val ideaActualRepository: IdeaActualRepository,
    val ideaHistoryRepository: IdeaHistoryRepository
) : IdeaInteractor {
    override fun handleIdea(title: String) {
        val idea = Idea(
            priority = Priority.LOW,
            idea = title,
            date = Date().toString()
        )

        ideaActualRepository.saveToActual(idea)
        ideaHistoryRepository.saveToHistory(idea)
    }
}