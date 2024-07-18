package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class SettingsViewModel (
    private val settingsRepository: SettingsRepository
): ViewModel() {

    val passCheckBoxState = settingsRepository.passCheckBoxState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
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

    suspend fun userSwitchedPassCheckBox(state: Boolean) {
        settingsRepository.passCheckBoxSetState(state)
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
}