package domain.di

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
        MainDb.getDb(context = get(), scope = get())
    }
    single<IdeasRepository> {
        IdeasRepository(dao = get())
    }
}