package domain.user_cases

import domain.repo.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DeleteTaskUseCase : KoinComponent {
    private val taskRepo : TaskRepo by inject()

    operator fun invoke(id : Long) = flow<Unit> {
        taskRepo.deleteTaskById(id)
    }.flowOn(Dispatchers.IO)
}