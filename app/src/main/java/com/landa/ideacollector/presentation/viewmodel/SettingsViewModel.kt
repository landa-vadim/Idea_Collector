package com.landa.ideacollector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val passwordCheckBoxStateFlow = settingsRepository.getPasswordEnableState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )
    val sortedTypeStateFlow = settingsRepository.sortedTypeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        SortType.DATE
    )
    val themeStateFlow = settingsRepository.themeFlow.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        Theme.LIGHT
    )
    private val _userEnteredPasswordFlow = MutableStateFlow(StateUserEnteredPassword.STARTEDSTATE)
    val userEnteredPasswordFlow = _userEnteredPasswordFlow.asStateFlow()
    private val _userSetPasswordFlow = MutableStateFlow(StateUserSetPassword.STARTEDSTATE)
    val userSetPasswordFlow = _userSetPasswordFlow.asStateFlow()
    private val _ideasIsLockedFlow = MutableStateFlow(true)
    val ideasIsLockedFlow = _ideasIsLockedFlow.asStateFlow()

    suspend fun userSwitchedPasswordCheckBox(state: Boolean) {
        settingsRepository.setPasswordEnableState(state)
    }

    fun userSetPassword(pass: String, confirm: String) {
        viewModelScope.launch {
            if (pass.isEmpty() || confirm.isEmpty()) {
                _userSetPasswordFlow.value = StateUserSetPassword.ONEFIELDISEMPTY
                return@launch
            }
            if (pass != confirm) {
                _userSetPasswordFlow.value = StateUserSetPassword.FIELDSNOTTHESAME
                return@launch
            }
            else {
                settingsRepository.setPasswordValue(pass)
                _userSetPasswordFlow.value = StateUserSetPassword.PASSWORDWASACCEPTED
            }
        }
    }

    suspend fun userChangedSortType() {
        val setValue =
            when (sortedTypeStateFlow.value) {
                SortType.DATE -> SortType.PRIORITY
                SortType.PRIORITY -> SortType.DATE
            }
        settingsRepository.setSortedType(setValue)
    }

    suspend fun userChangedTheme() {
        val setValue =
            when (themeStateFlow.value) {
                Theme.LIGHT -> Theme.DARK
                Theme.DARK -> Theme.LIGHT
            }
        settingsRepository.setTheme(setValue)
    }

    fun userEnteredPassword(enteredPass: String) {
        viewModelScope.launch {
            val isPassCorrect = settingsRepository.isPasswordCorrect(enteredPass)
            _userEnteredPasswordFlow.value =
                if (isPassCorrect) StateUserEnteredPassword.CORRECTPASS
                else StateUserEnteredPassword.WRONGPASS
            _ideasIsLockedFlow.emit(!isPassCorrect)
        }
    }

    fun setPasswordDialogDismiss() {
        _userSetPasswordFlow.value = StateUserSetPassword.STARTEDSTATE
    }

    fun askPasswordDialogDismiss() {
        _userEnteredPasswordFlow.value = StateUserEnteredPassword.STARTEDSTATE
    }
}

enum class StateUserEnteredPassword {
    STARTEDSTATE,
    WRONGPASS,
    CORRECTPASS
}

enum class StateUserSetPassword {
    STARTEDSTATE,
    ONEFIELDISEMPTY,
    FIELDSNOTTHESAME,
    PASSWORDWASACCEPTED
}