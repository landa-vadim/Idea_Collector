package com.landa.ideacollector.data.repository

import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DatastoreSettingsRepository(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override val isPassEnableStateFlow: Flow<Boolean> = dataStoreManager.isPassEnabled()

    override val passFlow: Flow<String> = dataStoreManager.getPassValue()

    override val sortedTypeFlow: Flow<SortType> = dataStoreManager.getSortType()

    override val themeFlow: Flow<Theme> = dataStoreManager.getTheme()

    override suspend fun setPassEnableState(enabled: Boolean) {
        dataStoreManager.isPassEnabled(enabled)
    }

    override suspend fun isPassCorrect(enteredPass: String): Boolean {
        val isPassCorrect = passFlow.first() == enteredPass
        return isPassCorrect
    }

    override suspend fun setPassValue(pass: String) {
        dataStoreManager.passValue(pass)
    }

    override suspend fun setSortedType(sortedType: SortType) {
        dataStoreManager.sortedTypeValue(sortedType)
    }

    override suspend fun setTheme(theme: Theme) {
        dataStoreManager.themeValue(theme)
    }


}