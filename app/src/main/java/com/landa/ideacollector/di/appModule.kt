package com.landa.ideacollector.di

import com.landa.ideacollector.presentation.viewmodel.MainViewModel
import com.landa.ideacollector.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel>{
        MainViewModel(ideasRepository = get(), settingsRepository = get())
    }
    viewModel<SettingsViewModel>{
        SettingsViewModel(settingsRepository = get())
    }

}