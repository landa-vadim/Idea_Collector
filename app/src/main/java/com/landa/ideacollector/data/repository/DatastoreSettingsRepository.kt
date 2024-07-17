package com.landa.ideacollector.data.repository

import com.landa.ideacollector.data.sharedprefs.ShrdPref
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.Flow

class DatastoreSettingsRepository(
    private val shrdPref: ShrdPref,
    private val dataStoreManager: DataStoreManager
) : SettingsRepository {

    override val passCheckBoxState: Boolean = shrdPref.passCheckBoxGetValue()

    override fun passCheckBoxSwitch(switch: Boolean) {
        shrdPref.passCheckBoxSetValue(switch)
    }

    override suspend fun setSortedType(sortedType: SortTypeEnum) {
        dataStoreManager.setSortedTypeValue(sortedType)
    }
    override suspend fun setTheme(theme: ThemeEnum) {
        dataStoreManager.setThemeValue(theme)
    }

    override val sortedType: Flow<SortTypeEnum> = dataStoreManager.getSortTypeValue()

    override val theme: Flow<ThemeEnum> = dataStoreManager.getThemeValue()
}