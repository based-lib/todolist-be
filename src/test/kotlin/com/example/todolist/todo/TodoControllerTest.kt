package com.example.todolist.todo

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.put

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `todo CRUD flow`() {
        val createRequest = CreateTodoRequest(
            title = "Write tests",
            description = "Add MockMvc tests"
        )

        val createResult = mockMvc.post("/api/todos") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(createRequest)
        }
            .andExpect {
                status { isCreated() }
            }
            .andReturn()

        val created = objectMapper.readValue(createResult.response.contentAsString, TodoResponse::class.java)
        assertThat(created.id).isPositive()
        assertThat(created.title).isEqualTo("Write tests")
        assertThat(created.completed).isFalse()

        mockMvc.get("/api/todos")
            .andExpect {
                status { isOk() }
            }

        mockMvc.get("/api/todos/${created.id}")
            .andExpect {
                status { isOk() }
            }

        val updateRequest = UpdateTodoRequest(
            title = "Write more tests",
            description = "Add service tests",
            completed = true
        )

        val updateResult = mockMvc.put("/api/todos/${created.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }
            .andExpect {
                status { isOk() }
            }
            .andReturn()

        val updated = objectMapper.readValue(updateResult.response.contentAsString, TodoResponse::class.java)
        assertThat(updated.title).isEqualTo("Write more tests")
        assertThat(updated.completed).isTrue()

        mockMvc.delete("/api/todos/${created.id}")
            .andExpect {
                status { isNoContent() }
            }

        mockMvc.get("/api/todos/${created.id}")
            .andExpect {
                status { isNotFound() }
            }
    }
}
