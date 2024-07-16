package domain.di

import domain.utilityClasses.IdeasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<IdeasViewModel>{
        IdeasViewModel(repository = get())
    }

}