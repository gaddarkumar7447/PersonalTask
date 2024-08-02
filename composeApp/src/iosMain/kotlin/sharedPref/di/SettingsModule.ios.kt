package sharedPref.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val settingModule: Module
    get() = module {
       single<Settings> {
           NSUserDefaultsSettings.Factory().create("iOSPref")
       }
    }