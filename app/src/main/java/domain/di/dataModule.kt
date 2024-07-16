package domain.di

import android.app.Application
import domain.IdeasRepository
import domain.application.IdeasApplication
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
    single<IdeasRepository> {
        IdeasRepository(dao = get())
    }
}