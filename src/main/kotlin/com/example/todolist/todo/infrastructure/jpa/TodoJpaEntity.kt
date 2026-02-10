package com.example.todolist.todo.infrastructure.jpa

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "todos")
class TodoJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String,

    @Column(nullable = true, length = 2000)
    var description: String?,

    @Column(nullable = false)
    var completed: Boolean,

    @Column(nullable = false)
    var createdAt: Instant,

    @Column(nullable = false)
    var updatedAt: Instant
)
