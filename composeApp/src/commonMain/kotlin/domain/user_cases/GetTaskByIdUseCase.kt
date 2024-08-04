package domain.user_cases

import domain.repo.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import model.TaskModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.ResultStatus

class GetTaskByIdUseCase : KoinComponent {
    private val taskRepo : TaskRepo by inject()

    operator fun invoke(id : Long) = flow<ResultStatus<TaskModel>>{
        emit(ResultStatus.Loading())
        emit(ResultStatus.Success(data = taskRepo.getNodeById(id)))
    }.catch {
        emit(ResultStatus.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}