package com.landa.ideacollector.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.landa.ideacollector.data.db.Dao
import com.landa.ideacollector.data.db.MainDb
import com.landa.ideacollector.data.repository.DataStoreManager
import com.landa.ideacollector.data.repository.DatastoreSettingsRepository
import com.landa.ideacollector.data.repository.RoomIdeasRepository
import com.landa.ideacollector.data.sharedprefs.ShrdPref
import com.landa.ideacollector.domain.interfaces.IdeasRepository
import com.landa.ideacollector.domain.interfaces.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

val dataModule = module {
    single<ShrdPref> {
        ShrdPref(context = get())
    }
    single<DataStore<Preferences>> {
        androidContext().dataStore
    }
    single<MainDb> {
        MainDb.getDb(context = get())
    }
    single<Dao> {
        MainDb.getDb(context = get()).getDao()
    }
    single<DataStoreManager> {
        DataStoreManager(dataStore = get())
    }
    single<IdeasRepository> {
        RoomIdeasRepository(dao = get())
    }
    single<SettingsRepository> {
        DatastoreSettingsRepository(shrdPref = get(), dataStoreManager = get())
    }
}