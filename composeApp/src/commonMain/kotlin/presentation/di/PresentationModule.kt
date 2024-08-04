package presentation.di

import org.koin.dsl.module
import presentation.screens.home.state.HomeScreenViewModel
import sharedPref.SettingsRepository

val presentationModule = module {
    factory { HomeScreenViewModel(get(), get(), get(), get()) }
    single { SettingsRepository() }
}