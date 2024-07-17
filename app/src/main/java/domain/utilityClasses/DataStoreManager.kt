package domain.utilityClasses

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {

    suspend fun setSortedTypeValue(sortType: Int) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("sort_type")] = sortType
        }
    }

    suspend fun setThemeValue(theme: Int) {
        context.dataStore.edit { pref ->
            pref[intPreferencesKey("theme")] = theme
        }
    }

    fun getSortTypeValue() = context.dataStore.data
        .map { pref ->
            pref[intPreferencesKey("sort_type")] ?: 0
        }

    fun getThemeValue() = context.dataStore.data
        .map { pref ->
            pref[intPreferencesKey("theme")] ?: 0
        }

}