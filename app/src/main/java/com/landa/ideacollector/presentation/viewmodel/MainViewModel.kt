package com.landa.ideacollector.presentation.viewmodel

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.R
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.domain.model.Priority
import com.landa.ideacollector.domain.model.SortTypeEnum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel(
    private val ideasRepository: IdeasRepository,
    private val settingsRepository: SettingsRepository
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
        SortTypeEnum.DATE
    )

    fun getSortedIdeas() =
        combine(ideasListFlow, sortedTypeFlow) { ideaList: List<Idea>, sortType: SortTypeEnum ->
            when (sortType) {
                SortTypeEnum.DATE -> ideaList.sortedBy { it.date }
                SortTypeEnum.PRIORITY -> ideaList.sortedBy { it.priority }
            }
        }

    fun userClickedDoneButton(string: String) {
        val ideaPriority = when (colorIndex) {
            0 -> Priority.HIGH
            1 -> Priority.MEDIUM
            2 -> Priority.LOW
            else -> return
        }
        val ideaText = string
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
        return when (priority) {
            Priority.HIGH -> R.color.red
            Priority.MEDIUM -> R.color.yellow
            Priority.LOW -> R.color.green
        }
    }
}