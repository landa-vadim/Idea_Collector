package domain.utilityClasses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.IdeasRepository
import data.dataClasses.Idea
import data.dataClasses.Priority
import data.dataClasses.SortTypeEnum
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

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

    fun userClickedDoneButton(string: String, color: Int) {
        val ideaPriority = when (color) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            2 -> Priority.LOW
            else -> return
        }
        val ideaText = string
        val ideaDate = Date().toString()
        val idea = Idea(
            null,
            ideaPriority,
            ideaText,
            ideaDate
        )
        viewModelScope.launch { repository.insertIdea(idea) }
    }

    fun userSwitchedPassCheckBox(switch: Boolean) {
        repository.passCheckBoxSwitch(switch)
    }

    fun passCheckBoxState(): Boolean = repository.passCheckBoxState
    fun userClickSetPassword() {}

    fun setSortedType() {

    }

    private val sortedType = repository
        .sortedType
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            0
        )

   private val theme = repository
       .theme
       .stateIn(
           viewModelScope,
           SharingStarted.Lazily,
           0
       )


}