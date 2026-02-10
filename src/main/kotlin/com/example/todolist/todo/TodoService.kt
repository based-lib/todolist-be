package com.example.todolist.todo

import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Clock
import java.time.Instant

@Service
class TodoService(
    private val repository: TodoRepository,
    private val clock: Clock
) {
    fun create(request: CreateTodoRequest): TodoResponse {
        val now = Instant.now(clock)
        val entity = TodoEntity(
            title = request.title.trim(),
            description = request.description?.trim(),
            completed = false,
            createdAt = now,
            updatedAt = now
        )
        return TodoResponse.from(repository.save(entity))
    }

    fun get(id: Long): TodoResponse = TodoResponse.from(findEntity(id))

    fun list(): List<TodoResponse> = repository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        .map(TodoResponse::from)

    fun update(id: Long, request: UpdateTodoRequest): TodoResponse {
        val entity = findEntity(id)
        entity.title = request.title.trim()
        entity.description = request.description?.trim()
        entity.completed = request.completed
        entity.updatedAt = Instant.now(clock)
        return TodoResponse.from(repository.save(entity))
    }

    fun delete(id: Long) {
        val entity = findEntity(id)
        repository.delete(entity)
    }

    private fun findEntity(id: Long): TodoEntity = repository.findById(id)
        .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found") }
}
