package domain.user_cases

import domain.repo.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import model.TaskModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import utils.ResultStatus

class GetAllTaskUseCase : KoinComponent {
    private val taskRepo: TaskRepo by inject()

    operator fun invoke() = flow<ResultStatus<List<TaskModel>>> {
        emit(ResultStatus.Loading())
        emit(ResultStatus.Success(data = taskRepo.getAllTask()))
    }.catch {
        emit(ResultStatus.Error(message = it.message.toString()))
    }.flowOn(Dispatchers.IO)
}