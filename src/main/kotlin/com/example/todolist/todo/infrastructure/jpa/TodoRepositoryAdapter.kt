package com.example.todolist.todo.infrastructure.jpa

import com.example.todolist.todo.domain.Todo
import com.example.todolist.todo.domain.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryAdapter(
    private val jpaRepository: TodoJpaRepository
) : TodoRepository {
    override fun save(todo: Todo): Todo {
        val entity = if (todo.id == null) {
            TodoJpaEntity(
                title = todo.title,
                description = todo.description,
                completed = todo.completed,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        } else {
            val existing = jpaRepository.findById(todo.id).orElse(null)
            if (existing != null) {
                existing.title = todo.title
                existing.description = todo.description
                existing.completed = todo.completed
                existing.updatedAt = todo.updatedAt
                existing
            } else {
                TodoJpaEntity(
                    id = todo.id,
                    title = todo.title,
                    description = todo.description,
                    completed = todo.completed,
                    createdAt = todo.createdAt,
                    updatedAt = todo.updatedAt
                )
            }
        }
        return jpaRepository.save(entity).toDomain()
    }

    override fun findById(id: Long): Todo? = jpaRepository.findById(id).orElse(null)?.toDomain()

    override fun findAll(): List<Todo> = jpaRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        .map(TodoJpaEntity::toDomain)

    override fun delete(todo: Todo) {
        if (todo.id != null) {
            jpaRepository.deleteById(todo.id)
        }
    }
}

private fun TodoJpaEntity.toDomain(): Todo = Todo.restore(
    id = id,
    title = title,
    description = description,
    completed = completed,
    createdAt = createdAt,
    updatedAt = updatedAt
)
