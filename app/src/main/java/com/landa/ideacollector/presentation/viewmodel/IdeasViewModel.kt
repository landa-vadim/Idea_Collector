package com.landa.ideacollector.presentation.viewmodel

import android.util.Log
import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.Idea
import com.landa.ideacollector.domain.model.Priority
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class IdeasViewModel(
    private val repository: IdeasRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val ideasListFlow = repository.ideasList.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    val sortedTypeFlow = settingsRepository.sortedType.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SortTypeEnum.DATE
    )

    val themeFlow = settingsRepository.theme.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        ThemeEnum.LIGHT
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
        viewModelScope.launch { repository.insertIdea(idea) }
    }

    fun userSwitchedPassCheckBox(switch: Boolean) {
        settingsRepository.passCheckBoxSwitch(switch)
    }

    fun passCheckBoxState(): Boolean = settingsRepository.passCheckBoxState

    suspend fun changeSortType() {
        e("VADIM", "collectingSortType")
        val setValue =
            when (sortedTypeFlow.value) {
                SortTypeEnum.DATE -> SortTypeEnum.PRIORITY
                SortTypeEnum.PRIORITY -> SortTypeEnum.DATE
            }
        settingsRepository.setSortedType(setValue)
    }

    suspend fun changeTheme() {
        Log.d("VADIM", "collectingTheme")
        val setValue =
            when (themeFlow.value) {
                ThemeEnum.LIGHT -> ThemeEnum.DARK
                ThemeEnum.DARK -> ThemeEnum.LIGHT
            }
        settingsRepository.setTheme(setValue)
    }
}