package com.landa.ideacollector.data.repository

import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DatastoreSettingsRepository(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override val passCheckBoxStateFlow: Flow<Boolean> = dataStoreManager.passCheckBoxGetState()

    override val passLockStateFlow: Flow<Boolean> = dataStoreManager.passLockGetState()

    override val passFlow: Flow<String> = dataStoreManager.passGetValue()

    override val sortedTypeFlow: Flow<SortTypeEnum> = dataStoreManager.sortTypeGetValue()

    override val themeFlow: Flow<ThemeEnum> = dataStoreManager.themeGetValue()

    override suspend fun passCheckBoxSetState(state: Boolean) {
        dataStoreManager.passCheckBoxSetState(state)
    }

    override suspend fun passLockSetState(passLockState: Boolean) {
        dataStoreManager.passLockState(passLockState)
    }

    override suspend fun isPassCorrect(enteredPass: String): Boolean {
        val isPassCorrect = passFlow.first() == enteredPass
        val isLockActive = !isPassCorrect
        passLockSetState(isLockActive)
        return isPassCorrect
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


}