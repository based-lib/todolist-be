package com.example.todolist.todo.domain

import java.time.Instant

class Todo private constructor(
    val id: Long?,
    title: String,
    description: String?,
    completed: Boolean,
    val createdAt: Instant,
    updatedAt: Instant
) {
    var title: String = title
        private set

    var description: String? = description
        private set

    var completed: Boolean = completed
        private set

    var updatedAt: Instant = updatedAt
        private set

    fun update(title: String, description: String?, completed: Boolean, now: Instant) {
        this.title = title.trim()
        this.description = description?.trim()
        this.completed = completed
        this.updatedAt = now
    }

    companion object {
        fun create(title: String, description: String?, now: Instant): Todo {
            val trimmedTitle = title.trim()
            require(trimmedTitle.isNotBlank()) { "title must not be blank" }
            return Todo(
                id = null,
                title = trimmedTitle,
                description = description?.trim(),
                completed = false,
                createdAt = now,
                updatedAt = now
            )
        }

        fun restore(
            id: Long,
            title: String,
            description: String?,
            completed: Boolean,
            createdAt: Instant,
            updatedAt: Instant
        ): Todo {
            return Todo(
                id = id,
                title = title,
                description = description,
                completed = completed,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
