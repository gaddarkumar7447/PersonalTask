package domain.user_cases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import model.TaskModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import domain.repo.TaskRepo

class InsertTaskUseCase : KoinComponent {

    private val taskRepo : TaskRepo by inject()

    operator fun invoke(taskModel: TaskModel) = flow<Unit> {
        taskRepo.insertTask(taskModel )
    }.flowOn(Dispatchers.IO)
}