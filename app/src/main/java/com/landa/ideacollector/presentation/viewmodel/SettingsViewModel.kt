package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val passCheckBoxStateFlow = settingsRepository.isPassEnableStateFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )
    val sortedTypeFlow = settingsRepository.sortedTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SortType.DATE
    )
    val themeFlow = settingsRepository.themeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        Theme.LIGHT
    )
    private val _ideasIsLockedFlow = MutableStateFlow(true)
    val ideasIsLockedFlow = _ideasIsLockedFlow.asStateFlow()

    suspend fun userSwitchedPassCheckBox(state: Boolean) {
        settingsRepository.setPassEnableState(state)
    }

    suspend fun userSetPassword(pass: String) {
        settingsRepository.setPassValue(pass)
    }

    suspend fun changeSortType() {
        val setValue =
            when (sortedTypeFlow.value) {
                SortType.DATE -> SortType.PRIORITY
                SortType.PRIORITY -> SortType.DATE
            }
        settingsRepository.setSortedType(setValue)
    }

    suspend fun userChangedTheme() {
        val setValue =
            when (themeFlow.value) {
                Theme.LIGHT -> Theme.DARK
                Theme.DARK -> Theme.LIGHT
            }
        settingsRepository.setTheme(setValue)
    }

    suspend fun userEnteredPassword(enteredPass: String): Boolean {
        val isPassCorrect = settingsRepository.isPassCorrect(enteredPass)
        return isPassCorrect
    }

    fun openedIdeas(isPassCorrect: Boolean) {
        _ideasIsLockedFlow.update {
            !isPassCorrect
        }
    }

}