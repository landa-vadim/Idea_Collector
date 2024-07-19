package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val passCheckBoxStateFlow = settingsRepository.passCheckBoxStateFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    val passLockStateFlow = settingsRepository.passLockStateFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    val sortedTypeFlow = settingsRepository.sortedTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SortTypeEnum.DATE
    )

    val themeFlow = settingsRepository.themeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        ThemeEnum.LIGHT
    )

    suspend fun userSwitchedPassCheckBox(state: Boolean) {
        settingsRepository.passCheckBoxSetState(state)
    }

    suspend fun userSetPassword(pass: String) {
        settingsRepository.passSetValue(pass)
    }

    suspend fun changeSortType() {
        val setValue =
            when (sortedTypeFlow.value) {
                SortTypeEnum.DATE -> SortTypeEnum.PRIORITY
                SortTypeEnum.PRIORITY -> SortTypeEnum.DATE
            }
        settingsRepository.sortedTypeSet(setValue)
    }

    suspend fun changeTheme() {
        val setValue =
            when (themeFlow.value) {
                ThemeEnum.LIGHT -> ThemeEnum.DARK
                ThemeEnum.DARK -> ThemeEnum.LIGHT
            }
        settingsRepository.themeSet(setValue)
    }

    suspend fun userEnteredPassword(enteredPass: String): Boolean {
        return settingsRepository.isPassCorrect(enteredPass)
    }

    suspend fun renewIdeasLockState() {
        settingsRepository.passLockSetState(false)
    }
}