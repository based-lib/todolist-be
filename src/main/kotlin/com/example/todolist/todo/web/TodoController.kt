package com.example.todolist.todo.web

import com.example.todolist.todo.application.TodoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val service: TodoService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: CreateTodoRequest): TodoResponse {
        val todo = service.create(request.title, request.description)
        return TodoResponse.from(todo)
    }

    @GetMapping
    fun list(): List<TodoResponse> = service.list()
        .map(TodoResponse::from)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): TodoResponse = TodoResponse.from(service.get(id))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: UpdateTodoRequest): TodoResponse {
        val todo = service.update(id, request.title, request.description, request.completed)
        return TodoResponse.from(todo)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        service.delete(id)
    }
}
