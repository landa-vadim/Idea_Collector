package com.landa.ideacollector.domain.interfaces

import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val passCheckBoxState: Boolean
    fun passCheckBoxSwitch(switch: Boolean)

    val sortedType: Flow<SortTypeEnum>

    val theme: Flow<ThemeEnum>
    suspend fun setSortedType(sortedType: SortTypeEnum)
    suspend fun setTheme(theme: ThemeEnum)
}