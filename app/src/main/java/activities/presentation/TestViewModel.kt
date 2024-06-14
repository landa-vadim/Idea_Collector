package activities.presentation

import activities.domain.interactor.IdeaInteractor
import activities.domain.repository.IdeaActualRepository
import androidx.lifecycle.ViewModel
import data.Idea
import kotlinx.coroutines.flow.StateFlow

class TestViewModel(val ideaActualRepository: IdeaActualRepository) : ViewModel() {

    val uiStateFlow: StateFlow<UIState>? = null

    init {
        val list = ideaActualRepository.loadData()
        uiStateFlow.emit(DataLoaded(list))
    }

    fun userClickedButton(string: String) {
        val idea = Idea()
        ideaActualRepository.saveToActual(idea)
//        interactor.handleIdea(string)
    }
}

sealed class UIState

class DataLoaded(val list: List<Idea>) : UIState()
class Error(errorDesc: String) : UIState()