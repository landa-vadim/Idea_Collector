package com.landa.ideacollector.data.repository

import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DatastoreSettingsRepository(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override val passCheckBoxState: Flow<Boolean> = dataStoreManager.passCheckBoxGetState()

    override val passFlow: Flow<String> = dataStoreManager.passGetValue()

    override val sortedType: Flow<SortTypeEnum> = dataStoreManager.sortTypeGetValue()

    override val theme: Flow<ThemeEnum> = dataStoreManager.themeGetValue()

    override suspend fun passCheckBoxSetState(state: Boolean) {
        dataStoreManager.passCheckBoxSetState(state)
    }

    override suspend fun passSetValue(pass: String) {
        dataStoreManager.passSetValue(pass)
    }

    override suspend fun sortedTypeSet(sortedType: SortTypeEnum) {
        dataStoreManager.sortedTypeSetValue(sortedType)
    }

    override suspend fun themeSet(theme: ThemeEnum) {
        dataStoreManager.themeSetValue(theme)
    }

    override suspend fun isPassCorrect(enteredPass: String): Boolean {
        return passFlow.first() == enteredPass
    }
}