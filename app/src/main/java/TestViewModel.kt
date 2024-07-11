import activities.domain.repository.IdeaActualRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class TestViewModel(val ideaActualRepository: IdeaActualRepository) : ViewModel() {

    val uiStateFlow: StateFlow<UIState>? = null

    init {
        val list = ideaActualRepository.loadData()
        uiStateFlow.emit(DataLoaded(list))
    }

    fun userClickedButton(string: String) {
        val ideaText = string
        val date = java.util.Date().toString()
        val priority = Priority.HIGH
        val idea = Idea(null, priority, ideaText, date)
        ideaActualRepository.saveToActual(idea)
        interactor.handleIdea(string, priority)
    }
}

sealed class UIState

class DataLoaded(val list: List<Idea>) : UIState()
class Error(errorDesc: String) : UIState()