package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val passCheckBoxState: Flow<Boolean>
    val passFlow: Flow<String>
    val sortedType: Flow<SortTypeEnum>
    val theme: Flow<ThemeEnum>
    suspend fun isPassCorrect(enteredPass: String): Boolean
    suspend fun passCheckBoxSetState(state: Boolean)
    suspend fun passSetValue(pass: String)
    suspend fun sortedTypeSet(sortedType: SortTypeEnum)
    suspend fun themeSet(theme: ThemeEnum)
}