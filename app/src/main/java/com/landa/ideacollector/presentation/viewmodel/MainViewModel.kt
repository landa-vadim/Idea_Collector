package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.domain.model.Priority
import com.landa.ideacollector.domain.model.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(
    private val ideasRepository: IdeasRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    val _currentPriority = MutableStateFlow(R.color.red)
    val currentPriority = _currentPriority.asStateFlow()

    val _currentPriorityEditIdea = MutableStateFlow(R.color.red)
    val currentPriorityEditIdea = _currentPriorityEditIdea.asStateFlow()

    private val ideasListFlow = ideasRepository.ideasList.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )
    private val sortedTypeFlow = settingsRepository.sortedTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SortType.DATE
    )

    fun getSortedIdeas() =
        combine(ideasListFlow, sortedTypeFlow) { ideaList: List<Idea>, sortType: SortType ->
            when (sortType) {
                SortType.DATE -> ideaList.sortedBy { it.date }
                SortType.PRIORITY -> ideaList.sortedBy { it.priority }
            }
        }

    fun userClickedDoneButton(ideaText: String) {
        val ideaPriority = when (currentPriority.value) {
            R.color.red -> Priority.HIGH
            R.color.yellow -> Priority.MEDIUM
            R.color.green -> Priority.LOW
            else -> return
        }
        val currentDate = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val ideaDate = currentDate.format(formatter).toString()
        val idea = Idea(
            null, ideaPriority, ideaText, ideaDate
        )
        viewModelScope.launch { ideasRepository.insertIdea(idea) }
    }

    suspend fun userClickedDeleteIdea(idea: Idea) {
        ideasRepository.deleteIdea(idea)
    }

    suspend fun userClickedEditIdea(idea: Idea, ideaText: String) {
        val id = idea.id
        val date = idea.date
        val ideaPriority = when (currentPriorityEditIdea.value) {
            R.color.red -> Priority.HIGH
            R.color.yellow -> Priority.MEDIUM
            R.color.green -> Priority.LOW
            else -> return
        }
        val newIdea = Idea(id, ideaPriority, ideaText, date)
        ideasRepository.editIdea(newIdea)
    }

    fun userClickedPriorityButton() {
        _currentPriority.value = when (currentPriority.value) {
            R.color.red -> R.color.yellow
            R.color.yellow -> R.color.green
            R.color.green -> R.color.red
            else -> R.color.red
        }
    }

    fun userClickedPriorityEditIdeaButton() {
        _currentPriorityEditIdea.value = when (_currentPriorityEditIdea.value) {
            R.color.red -> R.color.yellow
            R.color.yellow -> R.color.green
            R.color.green -> R.color.red
            else -> R.color.red
        }
    }

    fun getPriorityColor(priority: Priority) {
        _currentPriorityEditIdea.value = when (priority) {
            Priority.HIGH -> R.color.red
            Priority.MEDIUM -> R.color.yellow
            Priority.LOW -> R.color.green
        }
    }


}