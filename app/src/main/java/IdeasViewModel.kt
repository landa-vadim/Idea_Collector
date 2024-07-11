import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class IdeasViewModel(private val repository: IdeasRepository) : ViewModel() {
    private val ideasListFlow = repository
        .ideasList
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    fun insert(idea: Idea) {
        viewModelScope.launch {
            repository.insertIdea(idea)
        }
    }
    fun getSortedIdeas(): StateFlow<List<Idea>> {
        return ideasListFlow
    }
}