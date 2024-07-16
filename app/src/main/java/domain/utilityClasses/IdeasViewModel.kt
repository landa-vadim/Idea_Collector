package domain.utilityClasses

import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import domain.IdeasRepository
import data.dataClasses.Idea
import data.dataClasses.Priority
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import presentation.MainActivity
import presentation.SettingsActivity
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
}