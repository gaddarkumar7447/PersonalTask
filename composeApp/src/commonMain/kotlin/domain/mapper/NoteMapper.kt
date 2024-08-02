package domain.mapper

import databases.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import model.TaskModel

fun NoteEntity.toTask(): TaskModel {
    return TaskModel(
        id = id,
        title = title,
        content = content,
        status = status,
        created = Instant
            .fromEpochMilliseconds(created.toLong())
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}