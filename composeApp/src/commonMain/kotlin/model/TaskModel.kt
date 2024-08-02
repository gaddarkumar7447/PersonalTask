package model

import kotlinx.datetime.LocalDateTime


data class TaskModel(
    val id: Long?,
    val title: String,
    val content: String,
    val created: LocalDateTime,
    val status: String,
)