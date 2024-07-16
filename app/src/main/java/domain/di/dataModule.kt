package domain.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import domain.IdeasRepository
import domain.dataBase.Dao
import domain.dataBase.MainDb
import domain.utilityClasses.ShrdPref
import org.koin.dsl.module

val dataModule = module {
    single<ShrdPref> {
        ShrdPref(context = get())
    }
    single<MainDb> {
        MainDb.getDb(context = get())
    }
    single<Dao> {
        MainDb.getDb(context = get()).getDao()
    }
    single<DataStore<Preferences>> {
        val dataStore: DataStore<Preferences>
        dataStore
    }
    single<IdeasRepository> {
        IdeasRepository(dao = get(), shrdPref = get(), dataStore = get())
    }
}