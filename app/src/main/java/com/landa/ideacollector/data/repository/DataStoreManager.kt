package com.landa.ideacollector.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.landa.ideacollector.domain.model.SortTypeEnum
import com.landa.ideacollector.domain.model.ThemeEnum
import kotlinx.coroutines.flow.map

class DataStoreManager(val dataStore: DataStore<Preferences>) {

    suspend fun passCheckBoxSetState(checkBoxState: Boolean) {
        dataStore.edit { pref ->
            pref[booleanPreferencesKey("pass_check_box_state")] = checkBoxState
        }
    }

    suspend fun passLockState(isLockActive: Boolean) {
        dataStore.edit { pref ->
            pref[booleanPreferencesKey("pass_lock_state")] = isLockActive
        }
    }

    suspend fun passSetValue(pass: String) {
        dataStore.edit { pref ->
            pref[stringPreferencesKey("pass")] = pass
        }
    }

    suspend fun sortedTypeSetValue(sortType: SortTypeEnum) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("sort_type")] =
                when (sortType) {
                    SortTypeEnum.DATE -> 0
                    SortTypeEnum.PRIORITY -> 1
                }
        }
    }

    suspend fun themeSetValue(theme: ThemeEnum) {
        dataStore.edit { pref ->
            pref[intPreferencesKey("theme")] =
                when (theme) {
                    ThemeEnum.LIGHT -> 0
                    ThemeEnum.DARK -> 1
                }
        }
    }

    fun passCheckBoxGetState() = dataStore.data
        .map { pref ->
            val a = pref[booleanPreferencesKey("pass_check_box_state")] ?: false
            a
        }

    fun passLockGetState() = dataStore.data
        .map { pref ->
            pref[booleanPreferencesKey("pass_lock_state")] ?: true
        }

    fun passGetValue() = dataStore.data
        .map { pref ->
            pref[stringPreferencesKey("pass")] ?: "0000"
        }

    fun sortTypeGetValue() = dataStore.data
        .map { pref ->
            val sortType = pref[intPreferencesKey("sort_type")] ?: 0
            when (sortType) {
                0 -> SortTypeEnum.DATE
                1 -> SortTypeEnum.PRIORITY
                else -> SortTypeEnum.DATE
            }
        }

    fun themeGetValue() = dataStore.data
        .map { pref ->
            val theme = pref[intPreferencesKey("theme")] ?: 0
            when (theme) {
                0 -> ThemeEnum.LIGHT
                1 -> ThemeEnum.DARK
                else -> ThemeEnum.LIGHT
            }
        }


}