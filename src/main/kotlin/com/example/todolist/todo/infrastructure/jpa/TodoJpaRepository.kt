package com.example.todolist.todo.infrastructure.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface TodoJpaRepository : JpaRepository<TodoJpaEntity, Long>
