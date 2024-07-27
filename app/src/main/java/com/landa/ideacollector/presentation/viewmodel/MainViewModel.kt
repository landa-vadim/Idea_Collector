package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.domain.model.Priority
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.presentation.ui.MainActivity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(
    private val ideasRepository: IdeasRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private var colorIndex = 0
    private val colorList = listOf(R.color.red, R.color.yellow, R.color.green)
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
        val ideaPriority = when (colorIndex) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            2 -> Priority.LOW
            else -> return
        }
        val ideaDate = Date().toString()
        val idea = Idea(
            null, ideaPriority, ideaText, ideaDate
        )
        viewModelScope.launch { ideasRepository.insertIdea(idea) }
    }

    suspend fun userClickedDeleteIdea(idea: Idea) {
        ideasRepository.deleteIdea(idea)
    }

    suspend fun userClickedEditIdea(idea: Idea, ideaText: String, ideaPriorityColor: Int) {
        val id = idea.id
        val date = idea.date
        val ideaPriority = when (ideaPriorityColor) {
            R.color.red -> Priority.HIGH
            R.color.yellow -> Priority.MEDIUM
            R.color.green -> Priority.LOW
            else -> Priority.HIGH
        }
        val newIdea = Idea(id, ideaPriority, ideaText, date)
        ideasRepository.editIdea(newIdea)
    }

    fun userClickedPriorityButton(): Int {
        if (colorIndex > 1) colorIndex = -1
        return colorList[++colorIndex]
    }

    fun getPriorityColor(priority: Priority): Int {
        colorIndex = when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 1
            Priority.LOW -> 2
        }
        return colorList[colorIndex]
    }


}