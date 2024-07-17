package com.landa.ideacollector.di

import com.landa.ideacollector.presentation.viewmodel.IdeasViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<IdeasViewModel>{
        IdeasViewModel(repository = get(), settingsRepository = get())
    }

}