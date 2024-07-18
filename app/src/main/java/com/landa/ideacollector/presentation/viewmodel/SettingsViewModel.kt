package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var ideasIsLocked = true

    val passCheckBoxState = settingsRepository.passCheckBoxState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    val passFlow = settingsRepository.pass.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        "0000"
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
        val job = viewModelScope.launch {
            passFlow.collect { settedPassword ->
                if (enteredPass == settedPassword) {
                    ideasIsLocked = false
                    viewModelScope.cancel()
                }
            }
        }
        if (job.isCancelled) return true
        else return false
    }
}