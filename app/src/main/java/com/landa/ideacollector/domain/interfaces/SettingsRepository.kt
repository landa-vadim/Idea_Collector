package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val getPasswordEnableState: Flow<Boolean>
    val passwordFlow: Flow<String>
    val sortedTypeFlow: Flow<SortType>
    val themeFlow: Flow<Theme>
    suspend fun setPasswordEnableState(enabled: Boolean)
    suspend fun isPasswordCorrect(enteredPass: String): Boolean
    suspend fun setPasswordValue(pass: String)
    suspend fun setSortedType(sortedType: SortType)
    suspend fun setTheme(theme: Theme)
}