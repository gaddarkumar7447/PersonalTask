package domain.module

import domain.user_cases.DeleteTaskUseCase
import domain.user_cases.GetAllTaskUseCase
import domain.user_cases.GetTaskByIdUseCase
import domain.user_cases.InsertTaskUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { InsertTaskUseCase() }
    factory { DeleteTaskUseCase() }
    factory { GetAllTaskUseCase() }
    factory { GetTaskByIdUseCase() }
}