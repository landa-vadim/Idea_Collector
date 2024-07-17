package domain.utilityClasses

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import data.dataClasses.SortTypeEnum
import data.dataClasses.ThemeEnum
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {

    suspend fun setSortedTypeValue(sortType: SortTypeEnum) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("sort_type")] =
                when(sortType) {
                    SortTypeEnum.DATE -> 0
                    SortTypeEnum.PRIORITY -> 1
                }
        }
    }

    suspend fun setThemeValue(theme: ThemeEnum) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("theme")] =
                when(theme) {
                   ThemeEnum.LIGHT -> 0
                   ThemeEnum.DARK -> 1
                }
        }
    }

    fun getSortTypeValue() = context.dataStore.data
        .map { pref ->
            val sortType = pref[intPreferencesKey("sort_type")] ?: 0
            when (sortType) {
                0 -> SortTypeEnum.DATE
                1 -> SortTypeEnum.PRIORITY
                else -> SortTypeEnum.DATE
            }
        }

    fun getThemeValue() = context.dataStore.data
        .map { pref ->
            val theme = pref[intPreferencesKey("theme")] ?: 0
            when (theme) {
                0 -> ThemeEnum.LIGHT
                1 -> ThemeEnum.DARK
                else -> ThemeEnum.LIGHT
            }
        }

}