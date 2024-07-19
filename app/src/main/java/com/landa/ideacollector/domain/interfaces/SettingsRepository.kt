package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val passCheckBoxStateFlow: Flow<Boolean>
    val passFlow: Flow<String>
    val passLockStateFlow: Flow<Boolean>
    val sortedTypeFlow: Flow<SortTypeEnum>
    val themeFlow: Flow<ThemeEnum>
    suspend fun passCheckBoxSetState(state: Boolean)
    suspend fun passLockSetState(passLockState: Boolean)
    suspend fun isPassCorrect(enteredPass: String): Boolean
    suspend fun passSetValue(pass: String)
    suspend fun sortedTypeSet(sortedType: SortTypeEnum)
    suspend fun themeSet(theme: ThemeEnum)
}