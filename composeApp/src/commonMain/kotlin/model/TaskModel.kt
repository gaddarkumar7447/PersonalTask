package model

import kotlinx.datetime.LocalDateTime



data class TaskModel(
    val id: Long? = 0,
    val title: String,
    val content: String,
    val created: LocalDateTime,
    val status: String,
)