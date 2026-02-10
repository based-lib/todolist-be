package com.example.todolist.todo

import jakarta.validation.constraints.NotBlank
import java.time.Instant

data class CreateTodoRequest(
    @field:NotBlank
    val title: String,
    val description: String? = null
)

data class UpdateTodoRequest(
    @field:NotBlank
    val title: String,
    val description: String? = null,
    val completed: Boolean
)

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val completed: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(entity: TodoEntity): TodoResponse = TodoResponse(
            id = entity.id,
            title = entity.title,
            description = entity.description,
            completed = entity.completed,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }
}
