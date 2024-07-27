package com.landa.ideacollector.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.landa.ideacollector.domain.model.SortType
import com.landa.ideacollector.domain.model.Theme
import kotlinx.coroutines.flow.map

class DataStoreManager(val dataStore: DataStore<Preferences>) {

    suspend fun isPassEnabled(isPassEnabled: Boolean) {
        dataStore.edit { pref ->
            pref[booleanPreferencesKey("pass_check_box_state")] = isPassEnabled
        }
    }

    suspend fun passValue(pass: String) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey("pass")] = pass
        }
    }

    suspend fun sortedTypeValue(sortType: SortType) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("sort_type")] =
                when (sortType) {
                    SortType.DATE -> 0
                    SortType.PRIORITY -> 1
                }
        }
    }

    suspend fun themeValue(theme: Theme) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("theme")] =
                when (theme) {
                    Theme.LIGHT -> 0
                    Theme.DARK -> 1
                }
        }
    }

    fun isPassEnabled() = dataStore.data
        .map { pref ->
            pref[booleanPreferencesKey("pass_check_box_state")] ?: false
        }

    fun getPassValue() = dataStore.data
        .map { pref ->
            pref[stringPreferencesKey("pass")] ?: "0000"
        }

    fun getSortType() = dataStore.data
        .map { pref ->
            val sortType = pref[intPreferencesKey("sort_type")] ?: 0
            when (sortType) {
                0 -> SortType.DATE
                1 -> SortType.PRIORITY
                else -> SortType.DATE
            }
        }

    fun getTheme() = dataStore.data
        .map { pref ->
            val theme = pref[intPreferencesKey("theme")] ?: 0
            when (theme) {
                0 -> Theme.LIGHT
                1 -> Theme.DARK
                else -> Theme.LIGHT
            }
        }


}