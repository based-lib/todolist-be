package com.example.todolist.todo.application

import com.example.todolist.todo.domain.Todo
import com.example.todolist.todo.domain.TodoRepository
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
    fun create(title: String, description: String?): Todo {
        val now = Instant.now(clock)
        val todo = Todo.create(title, description, now)
        return repository.save(todo)
    }

    fun get(id: Long): Todo = findOrThrow(id)

    fun list(): List<Todo> = repository.findAll()

    fun update(id: Long, title: String, description: String?, completed: Boolean): Todo {
        val todo = findOrThrow(id)
        todo.update(title, description, completed, Instant.now(clock))
        return repository.save(todo)
    }

    fun delete(id: Long) {
        val todo = findOrThrow(id)
        repository.delete(todo)
    }

    private fun findOrThrow(id: Long): Todo = repository.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found")
}
