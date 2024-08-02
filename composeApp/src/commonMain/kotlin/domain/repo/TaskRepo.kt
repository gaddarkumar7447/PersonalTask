package domain.repo

import model.TaskModel

interface TaskRepo {
    suspend fun insertTask(taskModel: TaskModel)
    suspend fun updateTaskById(id : Long)
    suspend fun deleteTaskById(id: Long)
    suspend fun getNodeById(id: Long) : TaskModel?
    suspend fun getAllTask() : List<TaskModel>
}