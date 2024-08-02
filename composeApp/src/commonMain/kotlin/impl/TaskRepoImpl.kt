package impl

import app_db.AppDatabase
import kotlinx.datetime.toLocalDateTime
import domain.mapper.toTask
import model.TaskModel
import domain.repo.TaskRepo

class TaskRepoImpl(private val appDatabase: AppDatabase) : TaskRepo {
    override suspend fun insertTask(taskModel: TaskModel) {
        appDatabase.appDatabaseQueries.insertNote(
            id = taskModel.id,
            title = taskModel.title,
            content = taskModel.content,
            created = taskModel.created.toString(),
            status = taskModel.status
        )
    }

    override suspend fun updateTaskById(id: Long) {

    }

    override suspend fun deleteTaskById(id: Long) {
        appDatabase.appDatabaseQueries.deleteNodeById(id)
    }

    override suspend fun getNodeById(id: Long): TaskModel? {
        return appDatabase.appDatabaseQueries.getNoteById(id).executeAsOne().toTask()
    }

    override suspend fun getAllTask(): List<TaskModel> {
        return appDatabase.appDatabaseQueries.getAllNote().executeAsList().map {
            TaskModel(
                id = it.id,
                title = it.title,
                content = it.content,
                created = it.created.toLocalDateTime(),
                status = it.status
            )
        }
    }
}