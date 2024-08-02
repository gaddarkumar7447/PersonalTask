package sharedPref.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

actual val settingModule: Module
    get() = module {
        single<Settings> {
            val context : Context = get()
            SharedPreferencesSettings(context.getSharedPreferences("MyPref", Context.MODE_PRIVATE))
        }
    }