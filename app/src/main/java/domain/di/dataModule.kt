package domain.di

import domain.IdeasRepository
import domain.dataBase.Dao
import domain.dataBase.MainDb
import domain.utilityClasses.DataStoreManager
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
    single<DataStoreManager> {
        DataStoreManager(context = get())
    }
    single<IdeasRepository> {
        IdeasRepository(dao = get(), shrdPref = get(), dataStoreManager = get())
    }
}