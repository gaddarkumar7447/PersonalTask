package di

import app_db.AppDatabase
import local_db.SqlDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single { SqlDriverFactory(null).createSqlDriver() }
        single { AppDatabase.invoke(driver = get()) }

    }