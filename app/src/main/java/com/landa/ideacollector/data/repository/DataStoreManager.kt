package com.landa.ideacollector.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.map

class DataStoreManager(val dataStore: DataStore<Preferences>) {
    suspend fun setSortedTypeValue(sortType: SortTypeEnum) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("sort_type")] =
                when(sortType) {
                    SortTypeEnum.DATE -> 0
                    SortTypeEnum.PRIORITY -> 1
                }
        }
    }

    suspend fun setThemeValue(theme: ThemeEnum) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("theme")] =
                when(theme) {
                   ThemeEnum.LIGHT -> 0
                   ThemeEnum.DARK -> 1
                }
        }
    }

    fun getSortTypeValue() = dataStore.data
        .map { pref ->
            val sortType = pref[intPreferencesKey("sort_type")] ?: 0
            when (sortType) {
                0 -> SortTypeEnum.DATE
                1 -> SortTypeEnum.PRIORITY
                else -> SortTypeEnum.DATE
            }
        }

    fun getThemeValue() = dataStore.data
        .map { pref ->
            val theme = pref[intPreferencesKey("theme")] ?: 0
            when (theme) {
                0 -> ThemeEnum.LIGHT
                1 -> ThemeEnum.DARK
                else -> ThemeEnum.LIGHT
            }
        }

}