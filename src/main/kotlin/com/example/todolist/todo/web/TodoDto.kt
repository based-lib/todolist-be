package com.example.todolist.todo.web

import com.example.todolist.todo.domain.Todo
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
        fun from(todo: Todo): TodoResponse = TodoResponse(
            id = requireNotNull(todo.id),
            title = todo.title,
            description = todo.description,
            completed = todo.completed,
            createdAt = todo.createdAt,
            updatedAt = todo.updatedAt
        )
    }
}
