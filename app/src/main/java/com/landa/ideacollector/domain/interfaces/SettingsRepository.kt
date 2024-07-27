package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val isPassEnableStateFlow: Flow<Boolean>
    val passFlow: Flow<String>
    val sortedTypeFlow: Flow<SortType>
    val themeFlow: Flow<Theme>
    suspend fun setPassEnableState(enabled: Boolean)
    suspend fun isPassCorrect(enteredPass: String): Boolean
    suspend fun setPassValue(pass: String)
    suspend fun setSortedType(sortedType: SortType)
    suspend fun setTheme(theme: Theme)
}