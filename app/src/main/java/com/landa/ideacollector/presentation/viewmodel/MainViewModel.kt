package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            null, ideaPriority, ideaText, ideaDate
        )
        viewModelScope.launch { ideasRepository.insertIdea(idea) }
    }
    suspend fun userClickedDeleteIdea(idea: Idea) {
        ideasRepository.deleteIdea(idea)
    }
    suspend fun userClickedEditIdea(idea: Idea) {
        ideasRepository.editIdea(idea)
    }
}