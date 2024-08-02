package di

import app_db.AppDatabase
import domain.repo.TaskRepo
import impl.TaskRepoImpl
import org.koin.dsl.module

val dataModule = module {
    factory<TaskRepo> { TaskRepoImpl(get<AppDatabase>())  }
}