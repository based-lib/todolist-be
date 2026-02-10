package com.example.todolist.todo.domain

interface TodoRepository {
    fun save(todo: Todo): Todo
    fun findById(id: Long): Todo?
    fun findAll(): List<Todo>
    fun delete(todo: Todo)
}
