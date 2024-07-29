package com.landa.ideacollector.data.repository

import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DatastoreSettingsRepository(
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {
    override val getPasswordEnableState: Flow<Boolean> = dataStoreManager.getPasswordEnableState()

    override val passwordFlow: Flow<String> = dataStoreManager.getPasswordValue()

    override val sortedTypeFlow: Flow<SortType> = dataStoreManager.getSortType()

    override val themeFlow: Flow<Theme> = dataStoreManager.getTheme()

    override suspend fun setPasswordEnableState(enabled: Boolean) {
        dataStoreManager.setPasswordEnableState(enabled)
    }

    override suspend fun isPasswordCorrect(enteredPass: String): Boolean {
        val isPassCorrect = passwordFlow.first() == enteredPass
        return isPassCorrect
    }

    override suspend fun setPasswordValue(pass: String) {
        dataStoreManager.setPassword(pass)
    }

    override suspend fun setSortedType(sortedType: SortType) {
        dataStoreManager.setSortedType(sortedType)
    }

    override suspend fun setTheme(theme: Theme) {
        dataStoreManager.setTheme(theme)
    }


}