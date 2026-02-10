package com.example.todolist.todo

import com.example.todolist.todo.application.TodoService
import com.example.todolist.todo.domain.Todo
import com.example.todolist.todo.domain.TodoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import java.util.concurrent.atomic.AtomicLong

class TodoServiceTest {
    private val fixedClock = Clock.fixed(Instant.parse("2026-02-10T00:00:00Z"), ZoneOffset.UTC)

    @Test
    fun `create, update, delete flow`() {
        val repository = InMemoryTodoRepository()
        val service = TodoService(repository, fixedClock)

        val created = service.create("title", "desc")
        assertThat(created.id).isNotNull()
        assertThat(created.title).isEqualTo("title")
        assertThat(created.completed).isFalse()

        val updated = service.update(created.id!!, "new", "updated", true)
        assertThat(updated.title).isEqualTo("new")
        assertThat(updated.completed).isTrue()
        assertThat(updated.updatedAt).isEqualTo(Instant.parse("2026-02-10T00:00:00Z"))

        service.delete(created.id!!)
        assertThat(repository.findById(created.id!!)).isNull()
    }
}

private class InMemoryTodoRepository : TodoRepository {
    private val store = LinkedHashMap<Long, Todo>()
    private val sequence = AtomicLong(0)

    override fun save(todo: Todo): Todo {
        val saved = if (todo.id == null) {
            val id = sequence.incrementAndGet()
            Todo.restore(
                id = id,
                title = todo.title,
                description = todo.description,
                completed = todo.completed,
                createdAt = todo.createdAt,
                updatedAt = todo.updatedAt
            )
        } else {
            todo
        }
        store[saved.id!!] = saved
        return saved
    }

    override fun findById(id: Long): Todo? = store[id]

    override fun findAll(): List<Todo> = store.values.toList()

    override fun delete(todo: Todo) {
        if (todo.id != null) {
            store.remove(todo.id)
        }
    }
}
