package impl

import app_db.AppDatabase
import kotlinx.datetime.toLocalDateTime
import domain.mapper.toTask
import model.TaskModel
import domain.repo.TaskRepo

class TaskRepoImpl(private val appDatabase: AppDatabase) : TaskRepo {
    private val queries = appDatabase.appDatabaseQueries
    override suspend fun insertTask(taskModel: TaskModel) {
        queries.insertNote(
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
        queries.deleteNodeById(id)
    }

    override suspend fun getNodeById(id: Long): TaskModel? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toTask()
    }

    override suspend fun getAllTask(): List<TaskModel> {
        return queries.getAllNote().executeAsList().map {
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